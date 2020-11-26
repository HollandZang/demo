package com.holland.hadoop

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.Mapper
import org.apache.hadoop.mapreduce.Reducer
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat
import java.io.IOException
import java.net.URI
import java.util.*

fun main() {
    val hdfsPath = "hdfs://192.168.73.129:9000"
    val hdfs: FileSystem = FileSystem.get(URI(hdfsPath), Configuration())

    val job = Job.getInstance(hdfs.conf, "word count")
        .apply {
            mapperClass = MyMapper::class.java
            reducerClass = MyReducer::class.java
//            mapperClass = TokenizerMapper::class.java
//            reducerClass = IntSumReducer::class.java
//            outputKeyClass = Text::class.java
//            outputValueClass = IntWritable::class.java
            outputKeyClass = String::class.java
            outputValueClass = Int::class.java
        }

    FileInputFormat.addInputPath(job, Path("input"))
    FileOutputFormat.setOutputPath(job, Path("output"))

    println(job.waitForCompletion(true))
}

class MyMapper : Mapper<String, Int, String, Int>() {
    @Override
    override fun map(key: String?, value: Int?, context: Context?) {
//        super.map(key, value, context)
        val stringTokenizer = StringTokenizer(value.toString())
        if (stringTokenizer.hasMoreTokens()) {
            context?.write(stringTokenizer.nextToken(), 1)
        }
    }
}

class MyReducer : Reducer<String, Int, String, Int>() {
    @Override
    override fun reduce(key: String?, values: MutableIterable<Int>?, context: Context?) {
//        super.reduce(key, values, context)
        context?.write(key, values?.count())
    }
}

/*class MyMapper : Mapper<Any?, Text, Text?, IntWritable?>() {
    @Override
    override fun map(key: Any?, value: Text?, context: Context?) {
        val stringTokenizer = StringTokenizer(value.toString())
        if (stringTokenizer.hasMoreTokens()) {
            context?.write(Text(stringTokenizer.nextToken()), IntWritable(1))
        }
    }
}

class MyReducer : Reducer<Text?, IntWritable?, Text?, IntWritable?>() {
    @Override
    override fun reduce(key: Text?, values: MutableIterable<IntWritable?>?, context: Context?) {
        context?.write(key, values?.count()?.let { IntWritable(it) })
    }
}*/

class IntSumReducer :
    Reducer<Text?, IntWritable?, Text?, IntWritable?>() {
    private val result = IntWritable()

    @Throws(IOException::class, InterruptedException::class)
    public override fun reduce(key: Text?, values: Iterable<IntWritable?>, context: Context) {
        var sum = 0
        var `val`: IntWritable
        val var5: Iterator<*> = values.iterator()
        while (var5.hasNext()) {
            `val` = var5.next() as IntWritable
            sum += `val`.get()
        }
        result.set(sum)
        context.write(key, result)
    }
}

class TokenizerMapper :
    Mapper<Any?, Text, Text?, IntWritable?>() {
    private val word = Text()

    @Throws(IOException::class, InterruptedException::class)
    public override fun map(key: Any?, value: Text, context: Context) {
        val itr = StringTokenizer(value.toString())
        while (itr.hasMoreTokens()) {
            word.set(itr.nextToken())
            context.write(word, one)
        }
    }

    companion object {
        private val one = IntWritable(1)
    }
}
