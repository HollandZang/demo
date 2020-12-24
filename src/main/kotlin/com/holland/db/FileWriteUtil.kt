package com.holland.db

import java.io.File

object FileWriteUtil {

    fun mkdir(path: String) {
        val file = File(path)
        when (file.exists()) {
            true -> println("exists directory: $path")
            false -> {
                file.mkdir()
                println("create directory: $path")
            }
        }
    }

    fun string2File(stringBuilder: StringBuilder, path: String, fileName: String) {
        val file = File("$path\\$fileName.java")
        when (file.exists()) {
            true -> println("rebuild file: ${file.path}")
            false -> {
                file.createNewFile()
                println("create file: ${file.path}")
            }
        }
        file.writeText(stringBuilder.toString())
    }
}