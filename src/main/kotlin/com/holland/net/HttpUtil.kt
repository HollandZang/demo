package com.holland.net

import com.google.gson.Gson
import com.holland.GsonUtil
import com.holland.util.FileUtil
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

typealias OnResponse = (response: Response) -> Unit
typealias OnFailure = (exception: Exception) -> Unit


fun main() {
    FileUtil.readFile("D:\\practise\\demo\\src\\main\\kotlin\\com\\holland\\net", "data.txt")
        .forEachIndexed { index, it ->
            if (it.isNotBlank()) {
                val data = Gson().fromJson(it, Map::class.java)
                HttpUtil.get(
                    "http://localhost:9080/hwtdl/api/notice", data as Map<String, *>?,
                    {
                    val fromJson = Gson().fromJson(it.body!!.string(), Map::class.java)
                        if ((fromJson["meta"] as Map<String, String>)["code"] == "200") {
                            println("success:$index")
                        } else {
                            println("error:$index,,${(fromJson["meta"] as Map<String, String>)["message"]}")
                        }
                    },
                    { println("error:$index") }
                )
            }
        }
}

object HttpUtil {

    fun get(
        url: String,
        data: Map<String, *>?,
        onResponse: OnResponse?,
        onFailure: OnFailure?
    ) {
        val build = url.toHttpUrlOrNull()?.newBuilder().apply {
            data?.forEach { (t, u) -> this!!.addEncodedQueryParameter(t, u.toString()) }
        }!!.build()
        val request = Request.Builder()
            .url(build)
            .get()
            .build()
        BaseClient.baseRequestAsync(request, onResponse, onFailure)
    }

    fun postForm(
        url: String,
        data: Map<String, *>?,
        onResponse: OnResponse?,
        onFailure: OnFailure?
    ) {
        val formBody = FormBody.Builder().let {
            data?.forEach { (t, u) -> it.addEncoded(t, u.toString()) }
            it.build()
        }
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()
        BaseClient.baseRequestAsync(request, onResponse, onFailure)
    }

    fun postJson(
        url: String,
        data: Any?,
        onResponse: OnResponse?,
        onFailure: OnFailure?
    ) {
        val request = Request.Builder()
            .url(url)
            .post(GsonUtil.instance.toJson(data).toRequestBody())
            .build()
        BaseClient.baseRequestAsync(request, onResponse, onFailure)
    }
}

object BaseClient {
    private val INSTANCE = OkHttpClient.Builder()
        .connectTimeout(3, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    fun baseRequestAsync(
        request: Request,
        onResponse: OnResponse?,
        onFailure: OnFailure?
    ) {
        INSTANCE.newCall(request).enqueue(
            object : BaseCallback(request.url.encodedPath) {
                override fun onResponse(call: Call, response: Response) {
                    super.onResponse(call, response)
                    onResponse?.invoke(response)
                }

                override fun onFailure(call: Call, e: IOException) {
                    super.onFailure(call, e)
                    onFailure?.invoke(e)
                }
            }
        )
    }
}

abstract class BaseCallback(private val url: String?) : Callback {
    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
//        println("${this.javaClass.name}请求成功: $url")
    }

    override fun onFailure(call: Call, e: IOException) {
        println("${this.javaClass.name}请求失败: $url")
        e.printStackTrace()
    }
}