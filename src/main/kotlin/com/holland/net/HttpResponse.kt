package com.holland.net

class HttpResponse(originHeader: MutableMap<String, MutableList<String>>, private var body: String) {
    private val protocol: String
    private val code: Int
    private val status: String
    private val header: MutableMap<String, String> = mutableMapOf()

    init {
        var split = listOf("", "", "")
        originHeader.forEach { (k, v) ->
            if (k != null) header[k] = v[0] else split = v[0].split(" ")
        }
        this.protocol = split[0]
        this.code = split[1].toInt()
        this.status = split[2]
    }

    fun getProtocol() = this.protocol

    fun getCode() = this.code

    fun getStatus() = this.status

    fun getHeaders() = this.header

    fun getHeader(k: String) = this.header[k]

    fun getBody() = this.body
}