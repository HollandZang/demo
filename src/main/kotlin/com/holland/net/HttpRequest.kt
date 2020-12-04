package com.holland.net

import com.holland.util.StreamUtils
import org.jetbrains.annotations.NotNull
import java.net.HttpURLConnection
import java.net.Proxy
import java.net.URL


@Suppress("unused")
class HttpRequest {
    constructor(url: String) {
        connection = URL(url).openConnection() as HttpURLConnection
    }

    constructor(url: String, @NotNull proxy: Proxy?) {
        connection =
            if (proxy == null) URL(url).openConnection() as HttpURLConnection else URL(url).openConnection(proxy) as HttpURLConnection
    }

    private var connection: HttpURLConnection
    private var method: String = HttpMethod.GET.name
    private var headers: Map<String, Any>? = null

    fun method(m: HttpMethod): HttpRequest {
        this.method = m.name
        return this
    }

    fun putHeaderIfAbsent(k: String, @NotNull v: String?): HttpRequest {
        if (connection.requestProperties.containsKey(k) || v.isNullOrBlank()) return this
        connection.addRequestProperty(k, v)
        return this
    }

    /**
     * k: key
     * func: in_0 key, in_1 oldValue, out_2 newValue
     */
    fun putHeaderIfPresent(k: String, func: (String, String) -> String?): HttpRequest {
        // TODO: 2020/11/15 不能修改已存在的header
        val oldValue = connection.getRequestProperty(k)
        val newValue = func.invoke(k, oldValue)
        val mutableList = connection.requestProperties[k]
        mutableList?.set(0, newValue)
        return this
    }

    fun execute(): HttpResponse {
        connection.requestMethod = method
        return HttpResponse(connection.headerFields, StreamUtils.stream2String(connection.inputStream))
    }

    enum class HttpMethod {
        GET, POST
    }
}