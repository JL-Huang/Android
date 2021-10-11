package com.example.myapplication.MultiThread;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class TestIntentService extends IntentService {

    public TestIntentService(String name) {
        super(name);
    }
//    这里是源码
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        HandlerThread handlerThread=new HandlerThread("TestIntentService");
//        handlerThread.start();
//        mLooper=handlerThread.getLooper();
//        mServiceHandler=new ServiceHandler(mLooper);
//    }

    //@Override
//public void onStart(@android.annotation.Nullable Intent intent, int startId) {
//    Message msg = mServiceHandler.obtainMessage();
//    msg.arg1 = startId;
//    msg.obj = intent;
//    mServiceHandler.sendMessage(msg);
//}

//    每次启动Service都会调用onStartCommand方法，其内部是向上面的mServiceHandler发送启动时带来的Intent
//    结合下面的源码，可以知道这里真正调用的是重写的onHandleIntent
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
    //    这里是源码
//    private final class ServiceHandler extends Handler{
//        public ServiceHandler(Looper looper){
//            super(looper);
//        }
//
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            onHandleIntent((Intent) msg.obj);
//            stopSelf(msg.arg1);
//        }
//    }
}