package com.holland.util

import java.io.File

fun main(args: Array<String>) {
    val map = mapOf(
        Pair("00", '\u200b'),
        Pair("01", '\u200c'),
        Pair("10", '\u200d'),
        Pair("11", '\uFEFF')
    )

    val file = File("D:\\weather.txt")
    val str = "12啊?"

    val binaries = str.toByteArray().map { Integer.toBinaryString(it.toInt()) }
    val map1 = binaries.joinToString("") {
        var s = ""
        val chars = it.toCharArray()
        for (i in chars.indices step 2) {
            val string = String(charArrayOf(chars[i], chars[i + 1]))
            s += map[string]
        }
        s
    }

    for (i in map1.toCharArray().indices step 8) {
        var s = ""
        for (j in i..i + 7) {
            s+=map1.toCharArray()[i]
        }
        println(s)
    }

    file.writeText("显示内容$map1")

    val readLines = file.readLines()
    println(readLines)

    /**
    readLines[0].split("")
    .map {
    it.map { it.toInt().toChar() }
    }
    .filter { it.isNotEmpty() }
    .map {
    if (map.containsValue(it[0])) {
    var k=""
    map.entries.forEach { entry -> if (entry.value== it[0]){k=entry.key} }
    k
    }else it[0]
    }
     */
}
