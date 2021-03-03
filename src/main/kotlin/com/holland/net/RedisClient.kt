package com.holland.net

import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPubSub
import redis.clients.jedis.JedisShardInfo

class RedisClient {
}

fun main() {
    val shardInfo = JedisShardInfo("11.101.2.195", 6379).apply {
        password = "scxd@123"
    }
    val jedis = Jedis(shardInfo)
    val value = object : JedisPubSub() {
        override fun onSubscribe(channel: String?, subscribedChannels: Int) {
            super.onSubscribe(channel, subscribedChannels)
            println("$channel onSubscribe")
        }

        override fun onMessage(channel: String?, message: String?) {
            super.onMessage(channel, message)
            println("$channel onMessage: $message")
        }
    }
    jedis.subscribe(value, "c1","c2","c3")

}