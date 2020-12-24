package com.holland.db.service

import java.util.*

interface FetchTables {
    fun execute()
}

class TableTemplate(
    private val name: String,
    private val type: String,
    private val comment: String?
) {
    fun print() {
        Formatter(System.out)
            .format("name:%-20s\ttype:%-5s\t\tcomment:%-30s\n", name, type, comment)
    }
}