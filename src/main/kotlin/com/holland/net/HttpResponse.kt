package com.holland.net

@Suppress("unused", "MemberVisibilityCanBePrivate")
class HttpResponse(originHeader: MutableMap<String, MutableList<String>>, val body: String) {
    val protocol: String
    val code: Int
    val status: String
    val header: MutableMap<String, String> = mutableMapOf()

    init {
        lateinit var split: List<String>
        originHeader.forEach { (k, v) ->
            @Suppress("SENSELESS_COMPARISON")
            if (k != null) header[k] = v[0] else split = v[0].split(" ")
        }
        this.protocol = split[0]
        this.code = split[1].toInt()
        this.status = split[2]
    }

    fun getHeader(k: String) = this.header[k]

    override fun toString(): String {
        return "HttpResponse(body='$body', protocol='$protocol', code=$code, status='$status', header=$header)"
    }
}