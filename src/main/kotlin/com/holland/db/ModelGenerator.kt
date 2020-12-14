package com.holland.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class ModelGenerator(private val templates: Array<ModelTemplate>) {
    fun pojo() {
        templates.forEach {

        }
    }

}

class ModelTemplate(
    val columnName: String,
    val dataType: String,
    val charLength: Int,
    val nullable: Boolean,
    val dataDefault: String?,
    val comments: String?
) {
    override fun toString(): String {
        return "ModelTemplate(columnName='$columnName', dataType='$dataType', charLength=$charLength, nullable=$nullable, dataDefault=$dataDefault, comments=$comments)"
    }
}

fun dataTypeConvert(dataType: String): String {
    return when (dataType) {
        "CHAR", "VARCHAR2", "NVARCHAR2" -> "String"
        "DATE" -> "DATE"
        "NUMBER" -> "Long"
        else -> "Object"
    }
}

val url = "jdbc:oracle:thin:@11.101.2.195:1521/orcl"
val user = "yb_acd"
val pwd = "yb_acd"
val tableName = "SYS_USER"
var `package` = "com.stardon.stardon_main"

fun main() {
    val pojo = StringBuilder("""
        package $`package`.pojo;
        
        import lombok.Data;
        import lombok.experimental.Accessors;
        import org.springframework.format.annotation.DateTimeFormat;
        import javax.validation.constraints.*;
        import java.util.Date;
        """.trimIndent())
    Class.forName("oracle.jdbc.driver.OracleDriver")
    val connection = DriverManager.getConnection(url, user, pwd)

    val statement = connection.prepareStatement("select * from user_tab_comments where Table_Name=?")
    statement.setString(1, tableName)
    statement.execute()
    statement.resultSet.apply { next() }
        .apply {
            pojo.append("""
                
                        /*
                        * comment: ${this.getString("COMMENTS")}
                        */                
                         """.trimIndent())
                .append("\n@Data")
                .append("\n@Accessors(chain = true)")
                .append("\npublic class ${this.getString("TABLE_NAME")} {")
        }

    addProperties(connection, pojo)
    pojo.append("}")

    println(pojo.toString())

    try {
        statement.resultSet.close()
    } finally {
        try {
            statement.close()
        } finally {
            connection.close()
        }
    }

}

private fun addProperties(
    connection: Connection,
    pojo: StringBuilder
): Pair<PreparedStatement, ResultSet> {
    val statement =
        connection.prepareStatement("select * from user_tab_columns t inner join user_col_comments c on t.COLUMN_NAME=c.COLUMN_NAME and c.TABLE_NAME=? where t.TABLE_NAME=? order by COLUMN_ID")
    statement.setString(1, tableName)
    statement.setString(2, tableName)
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
            pojo.append("""
    /**
    * $comments
    */
""")
                .append(if (nullable) "" else "\t@NotNull\n")
                .append("\t@Size(max = $charLength,message = \"$columnName 长度不能大于$charLength\")\n")
                .append("\tprivate $dataType $columnName;\n")
        }
    }

    try {
        resultSet.close()
    } finally {
        statement.close()
    }

    return Pair(statement, resultSet)
}