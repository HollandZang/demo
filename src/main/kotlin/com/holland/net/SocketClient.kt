package com.holland.net

import java.net.ServerSocket
import java.net.Socket

class SocketClient {
    fun send() {
        val socket = Socket("192.168.73.129", 9092)
        socket.getOutputStream().write("Hello Socket".toByteArray())
        socket.shutdownOutput()
    }
}

fun main(args: Array<String>) {
    val socket = Socket("localhost", 8080)
    socket.getOutputStream().write("Hello Socket".toByteArray())
}

