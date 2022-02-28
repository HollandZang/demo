package com.holland.net

import com.holland.util.StreamUtils
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket


class ServerSocket

fun main() {
    val ip = 8080
    val server = ServerSocket(ip)

    println("开启成功：$ip")
    while (true) {
        val clientSocket: Socket = server.accept()

        val inputStream = clientSocket.getInputStream()
        val stream2String = StreamUtils.stream2String(inputStream)
        inputStream.close()
        println(stream2String)

        doResponse(clientSocket)

        clientSocket.close()
    }
}

fun doResponse(clientSocket: Socket) {
    val out = BufferedWriter(OutputStreamWriter(clientSocket.getOutputStream()))
    out.write("RECEIVED\r\n")
    out.close()
}
