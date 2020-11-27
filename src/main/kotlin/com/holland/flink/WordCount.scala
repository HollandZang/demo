package com.holland.flink

import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment

object WordCount {
  def main(args: Array[String]): Unit = {
    val executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment()
    val dataStreamSource: DataStream[String] = executionEnvironment.socketTextStream("192.168.73.129", 8082)

    val flatMap = dataStreamSource.flatMap()

    flatMap.keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)
      .print()

    executionEnvironment.execute("My word_count by Kotlin")
  }
}
