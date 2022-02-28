package com.holland.net

import java.net.Socket
import java.util.*

class SocketClient

fun main(args: Array<String>) {
    val socket = Socket("localhost", 8080)
    socket.getOutputStream().flush()
    socket.getOutputStream().write("Hello Socket ${Date()}".toByteArray())

    socket.close()
}

