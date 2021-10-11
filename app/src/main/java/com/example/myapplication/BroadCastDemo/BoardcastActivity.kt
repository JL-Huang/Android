package com.example.myapplication.BroadCastDemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_boardcast.*

class BroadcastActivity : AppCompatActivity() {
    lateinit var timeChangeReceiver:TimeChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boardcast)
//        初始化一个广播接收器
        timeChangeReceiver=TimeChangeReceiver();
//        初始化一个IntentFilter，并给他加一个action，以确定要过滤怎样的广播
        val intentFilter:IntentFilter= IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK")
//        注册广播接收器和过滤器
        registerReceiver(timeChangeReceiver,intentFilter);
        button_send.setOnClickListener{
            val intent=Intent("com.example.myapplication.MY_BROADCAST")
            intent.setPackage("com.example.myapplication.BroadCastDemo")
            sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        解注册广播接收器
        unregisterReceiver(timeChangeReceiver)
    }
//    继承BroadcastReceiver类
    inner class TimeChangeReceiver:BroadcastReceiver(){
//        收到广播触发onReceive
        override fun onReceive(context: Context?, intent: Intent?) {
            TODO("Not yet implemented")
            Toast.makeText(this@BroadcastActivity,"hhh",Toast.LENGTH_LONG).show()
        }

    }
}