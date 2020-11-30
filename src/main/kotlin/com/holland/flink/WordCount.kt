/*
package com.holland.flink

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.api.java.tuple.Tuple2
import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

fun main() {
    wordCount()
}


fun wordCount() {
    val executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment()
    val dataStreamSource: DataStream<String> = executionEnvironment.socketTextStream("192.168.73.129", 8082)

    val flatMap = dataStreamSource.flatMap(FlatMapFunction() { value: String, out: Collector<Tuple2<String, Long>> ->
//        value.split(" ").forEach { out.collect(Tuple2(it, 1L)) }
        out.collect(Tuple2(value, 1L))
    })

    flatMap.keyBy(0)
        .timeWindow(Time.seconds(5))
        .sum(1)
        .print()

    executionEnvironment.execute("My word_count by Kotlin")
}
*/
