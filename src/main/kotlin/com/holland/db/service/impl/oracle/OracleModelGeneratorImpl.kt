package com.holland.db.service.impl.oracle

import com.google.common.base.CaseFormat.*
import com.holland.db.DBController
import com.holland.db.service.ModelGenerator
import com.holland.db.service.ModelTemplate

/**
 * 集成Spring, Lombok, Swagger
 */
@Suppress("SqlDialectInspection", "SqlNoDataSourceInspection","unused", "DuplicatedCode", "ConvertTryFinallyToUseCall")
class OracleModelGeneratorImpl(private val dbController: DBController) : ModelGenerator {
    override val pojoBuilder = StringBuilder()
    override val className: String get() = UPPER_UNDERSCORE.to(UPPER_CAMEL, dbController.tableName)

    override fun getModel(): ModelGenerator {

        pojoBuilder.append(
            """
        package ${dbController.`package`}.${DBController.pojo};

        import lombok.Data;
        import lombok.experimental.Accessors;
        import org.springframework.format.annotation.DateTimeFormat;
        import javax.validation.constraints.*;
        import java.util.Date;
        import io.swagger.annotations.ApiModelProperty;
        """.trimIndent()
        )

        val statement = dbController.connection.prepareStatement("select * from user_tab_comments where Table_Name=?")
        statement.setString(1, dbController.tableName)
        statement.execute()
        statement.resultSet
            .apply {
                next()
                pojoBuilder.append(
                    """

                        /**
                         * comment: ${this.getString("COMMENTS")}
                         */
                         """.trimIndent()
                )
                    .append("\n@Data")
                    .append("\n@Accessors(chain = true)")
                    .append("\npublic class $className {")
            }

        addProperties()
        pojoBuilder.append("}")

        try {
            statement.resultSet.close()
        } finally {
            try {
                statement.close()
            } finally {
                dbController.connection.close()
            }
        }
        return this
    }

    private fun dataTypeConvert(dataType: String): String {
        return when (dataType) {
            "CHAR", "VARCHAR2", "NVARCHAR2" -> "String"
            "DATE" -> "Date"
            "NUMBER" -> "Long"
            "CLOB" -> "Object"
            else -> "Object"
        }
    }

    private fun addProperties() {
        val statement =
            dbController.connection.prepareStatement("select * from user_tab_columns t inner join user_col_comments c on t.COLUMN_NAME=c.COLUMN_NAME and c.TABLE_NAME=? where t.TABLE_NAME=? order by COLUMN_ID")
        statement.setString(1, dbController.tableName)
        statement.setString(2, dbController.tableName)
        statement.execute()
        val resultSet = statement.resultSet

        while (resultSet.next()) {
            val dataDefault = resultSet.getString("data_default")
            resultSet.run {
                ModelTemplate(
                    getString("COLUMN_NAME"),
                    dataTypeConvert(getString("data_type")),
                    getInt("char_length"),
                    "Y" == getString("nullable"),
                    dataDefault,
                    getString("comments")
                )
            }.apply {
                pojoBuilder.append(
                    """
    /**
     * $comments
     */
"""
                )
                    .append("\t@ApiModelProperty(value=\"$comments\")\n")
                    .append(if (nullable) "" else "\t@NotNull\n")
                    .append(if ("Date" == dataType) "\t@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n" else "")
                    .append("\t@Size(max = $charLength, message = \"$columnName 长度不能大于$charLength\")\n")
                    .append("\tprivate $dataType ${UPPER_UNDERSCORE.to(LOWER_CAMEL, columnName)};\n")
            }
        }

        try {
            resultSet.close()
        } finally {
            statement.close()
        }
    }
}
