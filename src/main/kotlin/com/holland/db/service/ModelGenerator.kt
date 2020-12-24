package com.holland.db.service

import com.holland.db.DBController
import com.holland.db.FileWriteUtil

interface ModelGenerator {
    val className: String
    val pojoBuilder: StringBuilder

    fun execute() = FileWriteUtil.string2File(pojoBuilder, DBController.pojo, className)

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