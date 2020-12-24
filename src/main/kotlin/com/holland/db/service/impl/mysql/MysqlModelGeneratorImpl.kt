package com.holland.db.service.impl.mysql

import com.google.common.base.CaseFormat.*
import com.holland.db.DBController
import com.holland.db.service.ModelGenerator
import com.holland.db.service.ModelTemplate

/**
 * 集成Spring, Lombok, Swagger
 */
class MysqlModelGeneratorImpl(private val dbController: DBController) : ModelGenerator {
    override val pojoBuilder = StringBuilder()
    override val className: String get() = UPPER_UNDERSCORE.to(UPPER_CAMEL, dbController.tableName)

    override fun getModel(): ModelGenerator {

        pojoBuilder.append(
            """
        package ${dbController.`package`}.pojo;

        import lombok.Data;
        import lombok.experimental.Accessors;
        import org.springframework.format.annotation.DateTimeFormat;
        import javax.validation.constraints.*;
        import java.math.BigDecimal;
        import java.util.Date;
        import io.swagger.annotations.ApiModelProperty;
        """.trimIndent()
        )

        val statement =
            dbController.connection.prepareStatement("select table_comment from information_schema.tables where table_schema=? and table_name=?")
        statement.setString(1, dbController.schema)
        statement.setString(2, dbController.tableName)
        statement.execute()
        statement.resultSet
            .apply {
                next()
                pojoBuilder.append(
                    """

                        /**
                         * comment: ${this.getString("TABLE_COMMENT")}
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
            "varchar", "char", "text", "mediumtext", "longtext" -> "String"
            "datetime", "timestamp", "date" -> "Date"
            "int", "bigint" -> "Long"
            "double", "float" -> "BigDecimal"
            else -> "Object"
        }
    }

    private fun addProperties() {
        val statement =
            dbController.connection.prepareStatement("select column_name,is_nullable,data_type,character_maximum_length,column_comment from information_schema.columns where table_schema=? and table_name=?")
        statement.setString(1, dbController.schema)
        statement.setString(2, dbController.tableName)
        statement.execute()
        val resultSet = statement.resultSet

        while (resultSet.next()) {
            resultSet.run {
                ModelTemplate(
                    getString("column_name"),
                    dataTypeConvert(getString("data_type")),
                    getInt("character_maximum_length"),
                    "Y" == getString("is_nullable"),
                    null,
                    getString("column_comment")
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
