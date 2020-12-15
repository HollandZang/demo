package com.holland.db

import java.io.File

interface ModelGenerator {
    val className: String
    val pojoBuilder: StringBuilder

    fun generate(path: String) {
        val file = File("$path\\${className}.java")
        if (!file.exists()) {
            file.createNewFile()
            println("create: ${file.path}")
        } else {
            println("rebuild: ${file.path}")
        }
        file.writeText(pojoBuilder.toString())
    }

    fun getModel(): ModelGenerator
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