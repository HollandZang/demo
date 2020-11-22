import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.common.xcontent.XContentType
import org.junit.jupiter.api.Test

class AppTest {

    private val client = RestHighLevelClient(
        RestClient.builder(
            HttpHost("localhost", 9200, "http"),
            HttpHost("localhost", 9201, "http")
        )
    )

    private val index = "blog"

    @Test
    fun healthy_check() {
        client.ping(RequestOptions.DEFAULT)
    }

    @Test
    fun create_index() {
        val request = CreateIndexRequest(index)
        request.source(
            """
             {
                    "settings" : {
                        "number_of_replicas" : 2
                    },
                    "properties": {
                        "content": {
                            "type": "text",
                            "analyzer": "ik_max_word",
                            "search_analyzer": "ik_smart"
                        }
                    }

            }
        """.trimIndent(), XContentType.JSON
        )
        client.indices().create(request, RequestOptions.DEFAULT)
    }

    @Test
    fun creat_doc() {
        val contents = arrayOf(
            "美国留给伊拉克的是个烂摊子吗",
            "公安部：各地校车将享最高路权",
            "中韩渔警冲突调查：韩警平均每天扣1艘中国渔船",
            "中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首"
        )

        contents.forEachIndexed { index, s ->
            run {
                val indexRequest = IndexRequest(this.index)
                    .id(index.toString())
                    .source(
                        """
                        {
                            "content": """" + s + """",
                            "title": "例""" + index + """"
                        }
                      """.trimIndent(), XContentType.JSON
                    )
                client.index(indexRequest, RequestOptions.DEFAULT)
            }
        }
    }
}