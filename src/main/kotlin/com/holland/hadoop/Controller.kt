package com.holland.hadoop

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FSDataOutputStream
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.io.IOException
import java.net.URI
import java.util.*
import javax.servlet.http.HttpServletResponse


@Api(value = "hadoop operator", tags = ["Hadoop操作"])
@RestController
@RequestMapping("/hadoop")
class Controller {
    /*hadoop dfsadmin -safemode leave*/
    @Value("\${hadoop.url}")
    private val hdfsPath = "hdfs://192.168.73.129:9000"
    private val hdfs: FileSystem = FileSystem.get(URI(hdfsPath), Configuration())

    @ApiOperation(value = "创建文件夹")
    @PostMapping("/dir")
    fun createDirectory(dir: String): Any {
        return try {
            val result: Boolean = hdfs.mkdirs(Path(dir))
            if (result) "Success" else "Failed!"
        } catch (e: IOException) {
            e.printStackTrace()
            e.localizedMessage
        }
    }

    @ApiOperation(value = "上传")
    @PostMapping("/file")
    fun createFile(path: String?, file: MultipartFile?): Any {
        if (file == null) {
            return "File is Empty"
        }
        return try {
            val outfile = Path((path ?: "") + file.originalFilename)
            if (hdfs.exists(outfile)) {
                val input = BufferedInputStream(file.inputStream)
                val output = hdfs.create(outfile)
                IOUtils.copyBytes(input, output, hdfs.conf, true)
                "Success"
            } else {
                "File Is Exists"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            e.localizedMessage
        }
    }

    //    @PutMapping("/file")
    fun appendFile(content: String, fileName: String): Any {
        val buff = content.toByteArray()
        val path = Path(fileName)
        val contentSummary = hdfs.getContentSummary(path)
        var os: FSDataOutputStream? = null
        return try {
            if (hdfs.exists(path)) {
                /*todo 暂不支持追加文件*/
                if (contentSummary.length.toInt() > 0) return "File's Content Is Not Empty...暂不支持追加文件"
                /*新写入文件*/
                os = hdfs.append(path)
                os.write(buff, contentSummary.length.toInt(), buff.size)
                os.flush()
                "Success"
            } else {
                "File Is Not Exists"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            e.localizedMessage
        } finally {
            os?.close()
        }
    }

    @ApiOperation(value = "下载")
    @GetMapping("/file")
    fun downloadFile(file: String, response: HttpServletResponse): Any? {
        return try {
            return if (hdfs.exists(Path(file))) {
                val `in` = hdfs.open(Path(file))
                val outputStream = response.outputStream
                response.contentType = "application/force-download"
                response.setHeader("Content-Disposition",
                    "attachment;fileName=" + UUID.randomUUID().toString() + file.split(",").last())
                response.setHeader("Content-length", hdfs.getContentSummary(Path(file)).length.toString())
                IOUtils.copyBytes(`in`, outputStream, hdfs.conf, true)
                `in`.close()
                outputStream.close()
                null
            } else {
                "File Is Not Exists"
            }
        } catch (e: IOException) {
            e.printStackTrace()
            e.localizedMessage
        }
    }

    private val wordTables = arrayOf("hello")

    @ApiOperation(value = "分词统计")
    fun wordAnalyze(input: String, output: String?) {
    }
}
