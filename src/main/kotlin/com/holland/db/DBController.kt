package com.holland.db

import com.holland.db.service.FetchTables
import com.holland.db.service.ModelGenerator
import com.holland.db.service.ServiceGenerator
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import kotlin.system.exitProcess

class DBController {
    lateinit var `package`: String
    lateinit var tableName: String
    var schema: String? = null
    var dataSource: String? = null

    lateinit var connection: Connection
    private lateinit var classPrefix: String

    constructor(url: String, user: String, pwd: String) {
        FileWriteUtil.mkdir(rootPath)
        initConnection(url, user, pwd)
    }

    constructor(url: String, user: String, pwd: String, `package`: String, tableName: String) {
        this.`package` = `package`
        this.tableName = tableName
        FileWriteUtil.mkdir(rootPath)
        initConnection(url, user, pwd)
    }

    fun fetchTables(): DBController {
        with(classPrefix) {
            Class.forName("com.holland.db.service.impl.${this.toLowerCase()}.${this}FetchTablesImpl")
                .getDeclaredConstructor(this@DBController::class.java)
                .newInstance(this@DBController)
                .apply {
                    this as FetchTables
                    execute()
                }
        }
        return this
    }

    fun generateAll() = this.generateFE().generateBE()

    fun generateFE() = this

    fun generateBE() = this.generateModel().generateService()

    fun generateModel(): DBController {
        FileWriteUtil.mkdir("$rootPath${File.separatorChar}${pojo}")
        with(classPrefix) {
            Class.forName("com.holland.db.service.impl.${this.toLowerCase()}.${this}ModelGeneratorImpl")
                .getDeclaredConstructor(this@DBController::class.java)
                .newInstance(this@DBController)
                .apply {
                    this as ModelGenerator
                    execute()
                }
        }
        return this
    }

    fun generateService(): DBController {
        FileWriteUtil.mkdir("$rootPath${File.separatorChar}${dao}")
        FileWriteUtil.mkdir("$rootPath${File.separatorChar}${dao}${File.separatorChar}impl")
        ServiceGenerator(this).initInterface().execute()
        return this
    }

    fun close() {
        if (connection.isClosed.not()) connection.close()
        println("connection is ${if (connection.isClosed.not()) "not" else ""}closed")
    }

    private fun initConnection(url: String, user: String, pwd: String) {
        Class.forName(
            when {
                url.contains("oracle") -> {
                    classPrefix = "Oracle"
                    this.tableName = tableName.toUpperCase()
                    dataSource = "ORACLE"
                    connection = DriverManager.getConnection(url, user, pwd)
                    "oracle.jdbc.driver.OracleDriver"
                }
                url.contains("mysql") -> {
                    classPrefix = "Mysql"
                    val split = url.split('/')[3].split('?')
                    schema = split[0]
                    connection =
                        DriverManager.getConnection(
                            if (split.size == 1) "$url?useUnicode=true&characterEncoding=utf8&useSSL=false&autoReconnect=true&serverTimezone=Asia/Shanghai" else url,
                            user,
                            pwd)
                    "com.mysql.cj.jdbc.Driver"
                }
                else -> {
                    println("not support [$url]")
                    exitProcess(0)
                }
            }
        )
    }

    companion object {
        const val rootPath = "generate"
        const val pojo = "pojo"
        const val dao = "service"
    }
}

fun main(args: Array<String>) {
    val url = "jdbc:oracle:thin:@11.101.2.36:1521/orcl"
    val user = "acd_ora"
    val pwd = "xiaomi"
//    val url = "jdbc:oracle:thin:@11.101.2.195:1521/orcl"
//    val user = "yb_acd"
//    val pwd = "yb_acd"
//    val url =
//        "jdbc:mysql://11.101.2.195:3306/lw_admin?useUnicode=true&characterEncoding=utf8&useSSL=false&autoReconnect=true&serverTimezone=Asia/Shanghai"
//    val user = "root"
//    val pwd = "123456"
    val `package` = "com.stardon.stardon_main"
    val tableName = "acd_file"
    DBController(url, user, pwd, `package`, tableName).fetchTables().close()

//    when (args[0]) {
//        "tables" -> {
//            DBController(args[1], args[2], args[3])
//                .fetchTables()
//        }
//        "generate" -> {
//            val dbController = DBController(args[1], args[2], args[3], args[4], args[5])
//                .generateModel()
//            dbController
//        }
//        else -> null
//    }?.close()
}