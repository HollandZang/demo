package com.holland.db

fun main() {
    CreateTable().execute()
}

class CreateTable {
    val tableName = "RISK_FLOW_OPINION"
    val tableComment = "隐患流程-整改意见表"
    val pk = ""
    val columns = "主键\tPK\tNUMBER\tX\n" +
            "流程ID\tFLOW_ID\tNUMBER\tX\n" +
            "附件列表(出具的整改意见)\tFILES\tVARCHAR2(511)\t\n" +
            "责任单位\tDEPT\tVARCHAR2(63)\tX\n" +
            "整改意见\tCONTENT\tVARCHAR2(511)\tX\n" +
            "附件列表(完成整改)\tFINISH_FILES\tVARCHAR2(511)\t\n" +
            "联系单位\tFINISH_CON_DEPT\tVARCHAR2(63)\t\n" +
            "联系人\tFINISH_CON_USER\tVARCHAR2(63)\t\n" +
            "联系方式\tFINISH_CONNECTION\tVARCHAR2(511)\t\n" +
            "完成整改描述\tFINISH_DESCRIPTION\tVARCHAR2(511)\t"

    fun execute() {
        val columns = columns.split('\n')


        println("""
                create table ${tableName.toUpperCase()}
                (
                ${
            columns.joinToString(",\n") {
                val list = it.split('\t')
                val base = list[1] + " " + list[2]
                val notNull = if (list[3] == "X") " not null " else ""
                val defaultDate = if (list[2] == "DATE") " default sysdate " else ""
                val pkVal = " constraint PK_ACD_FILE primary key "
                base + defaultDate + notNull + if (list[1] == pk) pkVal else ""
            }
        }
                )
                /
                comment on table ${tableName.toUpperCase()} is '${tableComment}'
                /
                
                ${
            columns.joinToString("\n") {
                val list = it.split('\t')
                "comment on column ${tableName.toUpperCase()}.${list[1]} is '${list[0]}' \n/"
            }
        }
                create sequence SEQ_${tableName.toUpperCase()}
                nocache
                /
                
                commit;
            """.trimIndent())
    }
}