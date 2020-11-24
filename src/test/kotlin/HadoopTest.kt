import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FSDataInputStream
import org.apache.hadoop.fs.FSDataOutputStream
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.Text
import org.junit.jupiter.api.Test
import java.io.IOException
import java.net.URI
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import java.util.regex.Pattern

class HadoopTest {
    /*hadoop dfsadmin -safemode leave*/
    val hdfsPath = "hdfs://192.168.73.129:9000"
    val hdfs: FileSystem = FileSystem.get(URI(hdfsPath), Configuration())

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
            /*todo 暂不支持追加文件*/
            if (contentSummary.length.toInt() > 0) return
            /*新写入文件*/
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

    val wordTables = arrayOf("hello")
    val separator = arrayOf('\n', '\t', ' ', ',', '.')

    @Test
    fun wordCount() {
        val input = "/user/root/input"
        val output = "/user/root/output"
        val path = Path(input)
        return try {
            if (hdfs.exists(path)) {
                val listFiles = hdfs.listFiles(path, false)
                val concurrentHashMap = ConcurrentHashMap<String, AtomicLong>()
                wordTables.forEach { word -> concurrentHashMap[word] = AtomicLong(0) }
                while (listFiles.hasNext()) {
                    val next = listFiles.next()
                    if (next.isFile) {
                        val `in`: FSDataInputStream = hdfs.open(next.path)
                        val readAllBytes = `in`.readAllBytes()
                        val text = Text(readAllBytes)
                        val str = text.toString()
                        val split = str.split(Pattern.compile("\n|\t|\\s|,|\\."))

                    }
                }
            } else {
                println("File Is Not Exists")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}