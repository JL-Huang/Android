package com.example.myapplication.NotificationTest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_notify.*

class NoticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)
        val manager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            创建消息渠道
//            第一个参数id。第二个是显示的名称
            val channel=NotificationChannel("zero","Zero",NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
            notice_send.setOnClickListener({
//                先创建一个intent，然后根据intent得到一个PendingIntent实例
//                PendingIntent可视为一个延迟触发的intent
                val intent=Intent(this,NoticeContentActivity::class.java)
                val pi=PendingIntent.getActivity(this,0,intent,0)
//                初始化通知格式及内容，返回一个通知对象
                val notification=NotificationCompat.Builder(this,"zero").setContentTitle("hhh")
                                .setContentText("哈哈哈").setContentIntent(pi).setAutoCancel(true).build()
//                启动通知，需要两个参数，第一个id，第二个通知对象
                manager.notify(1,notification)
            })
        }
    }
}