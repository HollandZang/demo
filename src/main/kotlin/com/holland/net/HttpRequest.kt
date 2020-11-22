package com.holland.net

import org.jetbrains.annotations.NotNull
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.Proxy
import java.net.URL


class HttpRequest {
    constructor(url: String) {
        connection = URL(url).openConnection() as HttpURLConnection
    }

    constructor(url: String, @NotNull proxy: Proxy?) {
        connection = if (proxy == null) URL(url).openConnection() as HttpURLConnection else URL(url).openConnection(proxy) as HttpURLConnection
    }

    private var connection: HttpURLConnection
    private var method: String = "GET"
    private var headers: Map<String, Any>? = null

    fun method(m: HttpMethod) {
        this.method = m.name
    }

    fun putHeaderIfAbsent(k: String, @NotNull v: String?) {
        if (connection.requestProperties.containsKey(k) || v.isNullOrBlank()) return
        connection.addRequestProperty(k, v)
    }

    /**
     * k: key
     * func: in_0 key, in_1 oldValue, out_2 newValue
     */
    fun putHeaderIfPresent(k: String, func: (String, String) -> String?) {
        // TODO: 2020/11/15 不能修改已存在的header
        val oldValue = connection.getRequestProperty(k)
        val newValue = func.invoke(k, oldValue)
        val mutableList = connection.requestProperties[k]
        mutableList?.set(0, newValue)
    }

    fun get(): HttpResponse {
        connection.requestMethod = method

        val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
        var line: String?
        val responseBody = StringBuilder()
        while (bufferedReader.readLine().also { line = it } != null) {
            responseBody.append(line)
        }
        connection.inputStream.close()
        return HttpResponse(connection.headerFields, responseBody.toString())
    }

    enum class HttpMethod(s: String) {
        GET("GET"), POST("POST")
    }
}
