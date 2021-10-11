package com.example.myapplication.WebDemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_web.*
import okhttp3.*
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.helpers.DefaultHandler
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        web_view.settings.javaScriptEnabled = true;
        web_view.webViewClient = WebViewClient();
        web_view.loadUrl("https://baidu.com")
        button_show.setOnClickListener {
//            tryHttpURLConnection()
            tryOKHttp()

        }
    }

    private fun tryHttpURLConnection() {
        thread {
            var connection: HttpURLConnection? = null
            try {
//                指定url
                var url = URL("https://www.baidu.com")
//                利用该url创建一个connection实例
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                connection.requestMethod = "GET"
//                根据connection属性获取一个输入流
                val input = connection.inputStream
//                也可以根据connection写一个输出流
                val output = connection.outputStream
                val writer = DataOutputStream(output)
//                写出数据
                writer.writeBytes("name=huang&password=123")
//                对输入流进行读取与其他操作
                val reader = BufferedReader(InputStreamReader(input))
                val response = StringBuilder()
//                ？这里的it没看懂
                reader.use {
                    reader.forEachLine { response.append(it) }//forEachLin方法需要一个(String) -> Unit类型参数
                }//输入参数不用指定类型，编译器会自动匹配对应的构造函数，创建对应实例
                showResponse(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect();
            }

        }
    }

    private fun showResponse(response: String) {
        runOnUiThread() {
            text_response.text = response
        }
    }

    private fun tryOKHttp() {
        thread {
            try {
                //        需要先创建一个客户端client实例，这不同于HttpURLConnection的创建connection连接
                val client = OkHttpClient()
//        发起一个GET请求，请求以request实例的形式保存
                val builder = Request.Builder().url("https://www.baidu.com")/*.get()*/;
                builder.addHeader("access_token","");
                val getRequest = builder.build()
//        通过newcall将请求绑定到cient，执行execute获取返回的数据
                val getResponse = client.newCall(getRequest).execute()
                val getResponseData = getResponse.body?.string()
//
////        如果是POST请求，还需要一个body
//        val postRequestBody=FormBody.Builder().add("name","huang").add("password","123").build()
//        val postRequest=Request.Builder().url("https://www.baidu.com").post(postRequestBody).build()
//        val postResponse=client.newCall(postRequest).execute()
//        val postResponseData = postResponse.body?.string()
                Log.e("dd", (getResponse == null).toString())
                if (getResponseData != null) {
                    showResponse(getResponseData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun parseXMLWithPull(xmlData: String) {
        try {
//            xmlPullParser实例是关键，通过工厂获取单例并新建对象获得
            val xmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
//            通过setinput方法将要解析的String类型xml数据传入xmlPullParser
            xmlPullParser.setInput(StringReader(xmlData))
//            通过xmlPullParser的eventType属性获取解析事件
            var eventType = xmlPullParser.eventType
            var id = ""
            var name = ""
            var version = ""
//            如果事件不为END_DOCUMENT，则通过name属性获取节点名称
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val nodeName = xmlPullParser.name
                when (eventType) {
//                    事件为START_TAG
                    XmlPullParser.START_TAG -> {
                        when (nodeName) {
//                            根据节点的不同，调用nextText获取对应值
                            "id" -> id = xmlPullParser.nextText()
                            "name" -> name = xmlPullParser.nextText()
                            "version" -> version = xmlPullParser.nextText()
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if ("app" == nodeName) {

                        }
                    }
                }
//                xmlPullParser的next方法可以获取下个事件
                eventType = xmlPullParser.next()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //    SAX解析需要一个DefaultHandler子类
    class ContentHandler : DefaultHandler() {
        private var nodeName = ""
        private lateinit var id: StringBuilder
        private lateinit var name: StringBuilder
        private lateinit var version: StringBuilder

        //        开始解析xml时调用
        override fun startDocument() {
            id = StringBuilder()
            name = StringBuilder()
            version = StringBuilder()
        }

        //        开始获取某个节点时调用
        override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes?) {
            nodeName = localName
        }

        //        解析节点过程中调用
        override fun characters(ch: CharArray?, start: Int, length: Int) {
            when (nodeName) {
                "id" -> id.append(ch, start, length)
                "name" -> name.append(ch, start, length)
                "version" -> version.append(ch, start, length)
            }
        }

        //        某个节点解析完时调用
        override fun endElement(uri: String?, localName: String?, qName: String?) {
            if ("app" == localName) {
                id.setLength(0)
                name.setLength(0)
                version.setLength(0)
            }
        }
    }

    private fun parseXMLWithSAX(xmlData: String) {
        try {
//            跟Pull解析一样，先通过工厂创建一个parse实例
            val saxParser = SAXParserFactory.newInstance().newSAXParser()
//            不同的是，pull解析可以直接通过StringReader读取String类型的xml数据，SAX解析还需要一个xmlReader，并将上面的contentHandler传入xmlReader
            val xmlReader = saxParser.xmlReader
            xmlReader.contentHandler = ContentHandler()
//            pull解析是通过parse实例的setinput方法传入StringReader读取的xml数据，sax解析是用xmlreader的parse方法传入InputSource
            xmlReader.parse(InputSource(StringReader(xmlData)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //    json数据为[{"name":"Tom","age":20},{"name":"Jack","age":25}]
    class Person(val name: String, val age: Int)

    private fun parseJSONWithGSON(jsonData: String) {
        val gson = Gson()
//        如果json不是数组的话直接解析
//        val applist=gson.fromJson(jsonData,Person::class.java)
//        如果是数组，先获取一个解析后的数据类型
//        ？这里写法没看懂
        val typeOf = object : TypeToken<List<Person>>() {}.type
        val applist = gson.fromJson<List<Person>>(jsonData, typeOf)
    }

    //    封装一个网络请求的工具类
    object HttpUtil {
        fun sendOKHttpRequest(adress: String, callback: okhttp3.Callback) {
            val client = OkHttpClient()
            val request = Request.Builder().url(adress).build()
//            在之前的代码中，newcall绑定client与request后执行的是execute()方法，返回一个Response实例
//            这里使用enqueue方法，该方法得到的Response实例直接交给输入参数中的callback处理
//            callback是回调接口，如果在工具类中开启线程然后在主线程获取子线程的结果，很有可能因为异步导致请求返回的结果传递不到主线程
//            因此在子线程中直接处理返回的结果，具体的处理逻辑可以在callback方法内写入
            client.newCall(request).enqueue(callback)
        }
    }
//    工具类的使用
//    HttpUtil.sendOKHttpRequest("https://baidu.com",object :Callback{
//        override fun onFailure(call: Call, e: IOException) {
//            TODO("Not yet implemented")
//        }
//
//        override fun onResponse(call: Call, response: Response) {
//            TODO("Not yet implemented")
//        }
//
//    })
}