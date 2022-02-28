package com.holland.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class ConsumerDemo {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        //主机信息
        properties.put("bootstrap.servers", "localhost:9092");
        //群组id
        properties.put("group.id", "group-4");
        /**
         *消费者是否自动提交偏移量，默认是true
         * 为了经量避免重复数据和数据丢失，可以把它设为true,
         * 由自己控制核实提交偏移量。
         * 如果设置为true,可以通过auto.commit.interval.ms属性来设置提交频率
         */
        properties.put("enable.auto.commit", "false");
        /**
         * 自动提交偏移量的提交频率
         */
        properties.put("auto.commit.interval.ms", "1000");
        /**
         * 默认值latest.
         * latest:在偏移量无效的情况下，消费者将从最新的记录开始读取数据
         * erliest:偏移量无效的情况下，消费者将从起始位置读取分区的记录。
         */
        properties.put("auto.offset.reset", "earliest");
        /**
         * 消费者在指定的时间内没有发送心跳给群组协调器，就被认为已经死亡，
         * 协调器就会触发再均衡，把它的分区分配给其他消费者。
         */
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        /**
         * 订阅主题，这个地方只传了一个主题：quickstart-events.
         * 这个地方也可以有正则表达式。
         */
        kafkaConsumer.subscribe(Collections.singletonList("quickstart-events"));
        //无限循环轮询
        while (true) {
            /**
             * 消费者必须持续对Kafka进行轮询，否则会被认为已经死亡，他的分区会被移交给群组里的其他消费者。
             * poll返回一个记录列表，每个记录包含了记录所属主题的信息，
             * 记录所在分区的信息，记录在分区里的偏移量，以及键值对。
             * poll需要一个指定的超时参数，指定了方法在多久后可以返回。
             * 发送心跳的频率，告诉群组协调器自己还活着。
             */
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                Thread.sleep(1000);
                System.err.printf("offset = %d, value = %s\n", record.offset(), record.value());
            }
        }
    }
}