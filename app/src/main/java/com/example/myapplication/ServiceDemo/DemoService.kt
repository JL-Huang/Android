package com.example.myapplication.ServiceDemo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class DemoService : Service() {
//    在onBind里返回一个实际执行操作的Binder子类
    private val mBinder=DownloadBinder();
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
        return mBinder;
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
//    建立一个继承自Binder的类，用于实现后台操作
    class DownloadBinder:Binder(){
        fun startDownload(){
            print("开始下载")
        }
        fun stopDownload(){
            print("停止下载")
        }
    }
}