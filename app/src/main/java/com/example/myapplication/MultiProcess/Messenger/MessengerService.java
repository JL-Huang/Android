package com.example.myapplication.MultiProcess.Messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

public class MessengerService extends Service {
    private static final String TAG = "MessengerService";
    //    接收器类的实例在onbind中调用
    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    public MessengerService() {
    }



    //    定义一个继承自handler的接收器类
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Log.e(TAG, "收到来电" + msg.getData().getString("msg"));
//                    需要向客户端返回数据的话，需要使用传来msg的replyTo字段
//                    该字段记录了发出该msg的Messenger
                    Messenger client = msg.replyTo;
//                    返回数据的载体依然是Message
                    Message replyMsg = Message.obtain(null, 0);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "回复来电");
                    replyMsg.setData(bundle);
                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mMessenger.getBinder();
    }
}