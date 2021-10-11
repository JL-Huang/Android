package com.example.myapplication.ServiceDemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_service_test.*
import java.util.*
import kotlin.concurrent.thread

class ServiceTestActivity : AppCompatActivity() {
//    Binder是两者沟通的载体，因此两边都需要有一个实例
    lateinit var mBinder: DemoService.DownloadBinder;
//    ServiceConnection实例实现两者的连接
    private val connection=object : ServiceConnection {
//    bindservice绑定成功时调用
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            TODO("Not yet implemented")
//            mBinder此时已经是对应Service里onbind返回的实例
//            as可以由父类转化为子类，使用主类的属性与方法
            mBinder=service as DemoService.DownloadBinder;
            mBinder.startDownload();
            mBinder.stopDownload();
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            TODO("Not yet implemented")
        }

    }
//    object修饰表示单例,同时也可表示匿名内部类
//    ？这里没看懂，为什么Handle后面会带括号
    val handler = object:Handler(){
    override fun handleMessage(msg: Message) {
        when(msg.what){
            1-> print("hhh")
        }
    }
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_test)
    }
    fun initButton(){
        button_message.setOnClickListener(){
            thread {
                val msg=Message();
                msg.what=1;
                msg.obj="ddd"
                handler.sendMessage(msg)
            }
        }
        button_start_service.setOnClickListener(){
//            ::class.java表示获取Class
            val intent= Intent(this,DemoService::class.java)
//            直接启动，不传递信息，执行onstratcommand，若该service还没创建会先执行oncreate
//            每start一次回调一次
//            startService(intent)
//            绑定启动,回调onbind第三个参数意思是绑定后自动创建Service,执行oncreate不执行onstratcommand
            bindService(intent,connection, Context.BIND_AUTO_CREATE);
        }
        button_stop_service.setOnClickListener(){
            val intent= Intent(this,DemoService::class.java)
//            两种都会调用ondestroy，如果既start又bind了，就必须下面两句都执行才能终结Service
            stopService(intent)
            unbindService(connection)
        }

    }
}