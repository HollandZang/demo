package com.holland.db

interface FetchTables {
    fun execute()
}

class TableTemplate(
    val name: String,
    val type: String,
    val comment: Int
) {
    override fun toString(): String {
        return "TableTemplate(name='$name', type='$type', comment=$comment)"
    }
}