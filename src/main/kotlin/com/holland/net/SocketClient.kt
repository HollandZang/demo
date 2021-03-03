package com.holland.net

import java.net.Socket

class SocketClient {
}

fun main(args: Array<String>) {
    val socket = Socket("localhost", 8080)
    socket.getOutputStream().write("Hello Socket".toByteArray())
}

