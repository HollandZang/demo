package com.holland.db.service.impl.oracle

import com.holland.db.DBController
import com.holland.db.service.FetchTables
import com.holland.db.service.TableTemplate

class OracleFetchTablesImpl(private val dbController: DBController) : FetchTables {
    override fun execute() {
        val statement = dbController.connection.prepareStatement("select * from user_tab_comments")
        statement.execute()
        statement.resultSet.apply {
            while (next()) {
                TableTemplate(
                    getString("table_name"),
                    getString("table_type"),
                    getString("comments")
                ).print()
            }
        }

        try {
            statement.resultSet.close()
        } finally {
            try {
                statement.close()
            } finally {
                dbController.connection.close()
            }
        }
    }
}