package com.example.myapplication.MultiProcess.Messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.example.myapplication.R;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";
    private Messenger mService;
//    设置当前Messenger实例，需要指定接收器handler
    private final Messenger mReplyMessenger = new Messenger(new MessengerHandler());
    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    Log.e(TAG,"收到回复来电"+msg.getData().getString("reply"));
                    break;
                default:
                    break;
            }
        }
    }
    private ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService=new Messenger(service);
//            Messenger传输信息，载体是Message，只能携带what,arg1,arg2,Bundle,object,replyTo
//            注意，自定义的Parcelable无法通过object字段传输
            Message msg=Message.obtain(null,1);
            Bundle data=new Bundle();
            data.putString("msg","客户端来电");
            msg.setData(data);
//            这句很关键，设置reolyto字段为当前Messenger,相当于署名
            msg.replyTo=mReplyMessenger;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent=new Intent(this,MessengerService.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}