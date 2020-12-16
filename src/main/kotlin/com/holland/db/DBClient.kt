package com.holland.db

import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class DBClient(val url: String, val user: String, val pwd: String, var tableName: String, val `package`: String) {
    val connection: Connection

    private val classPrefix: String

    init {
        initDir()
        Class.forName(
            if (url.contains("oracle")) {
                classPrefix = "Oracle"
                tableName = tableName.toUpperCase()
                "oracle.jdbc.driver.OracleDriver"
            } else {
                classPrefix = "Mysql"
                "com.mysql.cj.jdbc.Driver"
            }
        )
        this.connection = DriverManager.getConnection(url, user, pwd)
    }

    fun generateModel(): DBClient {
        Class.forName("com.holland.db.${classPrefix.toLowerCase()}.${classPrefix}ModelGeneratorImpl")
            .getDeclaredConstructor(this::class.java)
            .newInstance(this)
            .apply {
                this as ModelGenerator
                getModel().generate(dir)
            }
        return this
    }

    private fun initDir() {
        val file = File(dir)
        if (!file.exists()) {
            file.mkdir()
            println("create: $dir")
        } else {
            println("exists: $dir")
        }
    }

    companion object {
        const val dir = "generate"
    }
}

fun main(args: Array<String>) {
    when (args[0]) {
        "tables" -> println("todo tables")
        "generate" -> DBClient(
            args[1],
            args[2],
            args[3],
            args[4],
            args[5]
        ).generateModel()
    }
}