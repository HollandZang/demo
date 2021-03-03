package com.holland.rabbitmq

import com.rabbitmq.client.*

object RabbitConnection {
    private fun newConnection(): Connection {
        return ConnectionFactory().apply {
            host = "localhost"
            port = 5672
            virtualHost = "/"
            username = "guest"
            username = "guest"
        }.newConnection()
    }

    fun publish() {
        val connection = newConnection()
        val channel = connection.createChannel()
        channel.queueDeclare("c1", false, false, false, null)
        channel.basicPublish("", "c1", null, "hello1".toByteArray())

        channel.close()
        connection.close()
    }

    fun consume() {
        val connection = newConnection()
        val channel = connection.createChannel()
        channel.queueDeclare("c1", false, false, false, null)

        channel.basicConsume("c1", true, object : DefaultConsumer(channel) {

            override fun handleDelivery(
                consumerTag: String?,
                envelope: Envelope?,
                properties: AMQP.BasicProperties?,
                body: ByteArray?
            ) {
                super.handleDelivery(consumerTag, envelope, properties, body)
                val string = String(body!!)
                println("$consumerTag\t$envelope\t$properties\t$string")
            }
        })

        channel.close()
        connection.close()
    }
}

fun main() {
//    RabbitConnection.publish()
    RabbitConnection.consume()
}