package com.holland.util

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class StreamUtils {
    fun stream2String(inputStream: InputStream) = String(inputStream.readBytes())

    @Deprecated(message = "java's way to convert stream to string", replaceWith = ReplaceWith("stream2String"))
    fun stream2String_java(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        val responseBody = StringBuilder()
        while (bufferedReader.readLine().also { line = it } != null) {
            responseBody.append(line)
        }
        inputStream.close()
        return bufferedReader.toString()
    }
}