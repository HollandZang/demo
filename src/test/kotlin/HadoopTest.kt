/*
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FSDataOutputStream
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.junit.jupiter.api.Test
import java.net.URI

class HadoopTest {
    private val hdfsPath = "hdfs://192.168.73.129:9000"
    private val hdfs: FileSystem = FileSystem.get(URI(hdfsPath), Configuration())

    @Test
    fun create_directory() {
        val newDir = "/hdfstest"
        val result: Boolean = hdfs.mkdirs(Path(newDir))
        if (result) {
            println("Success!")
        } else {
            println("Failed!")
        }
    }

    @Test
    fun create_document() {
        val path = Path("/doc1")
        val boolean = hdfs.createNewFile(path)
        println(boolean)
    }

    @Test
    fun append_document() {
        val buff = "测试String".toByteArray()
        val path = Path("/doc1")
        val contentSummary = hdfs.getContentSummary(path)
        if (hdfs.exists(path)) {
            */
/*todo 暂不支持追加文件*//*

            if (contentSummary.length.toInt() > 0) return
            */
/*新写入文件*//*

            var os: FSDataOutputStream? = null
            try {
                os = hdfs.append(path)
                os.write(buff, contentSummary.length.toInt(), buff.size)
                os.flush()
            } finally {
                os?.close()
                hdfs.close()
            }
        }

    }

    @Test
    fun download_document() {
        hdfs.copyToLocalFile(false, Path("/doc1"), Path("D:\\"))
        println("OK")
    }
}*/
