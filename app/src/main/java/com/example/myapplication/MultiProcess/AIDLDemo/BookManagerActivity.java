package com.example.myapplication.MultiProcess.AIDLDemo;

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
import android.os.RemoteException;
import android.util.Log;

import com.example.myapplication.R;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {
    public static final String TAG = "BookManagerActivity";
    private IBookManager mIBookManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent=new Intent(this,BookManagerService.class);
        bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            关键代码，创建aidl对象，并将其实例化为Service所传递的IBinder，之后就可以通过该aidl对象调用service里所重写的接口方法
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            List<Book> list = null;
            try {
                mIBookManager=bookManager;
                list = bookManager.getBookList();
//                注意，aidl内部会自动转化，service写的是CopyOnWriteArrayList，这里得到的是Arraylist
//                Log.e(TAG,"类"+list.getClass().getCanonicalName());
                Log.e(TAG, String.valueOf(bookManager.getBookList().size()));
                Log.e(TAG, String.valueOf(mIOnNewBookArrivedListener==null));
//                关键代码，获取了别的进程的binder对象，调用了别的进程的binder对象的方法，传递一个本进程的变量
                bookManager.registerListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIBookManager=null;
        }
    };
    private IOnNewBookArrivedListener mIOnNewBookArrivedListener=new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(0,newBook).sendToTarget();
        }
//            这里一定要注释或者改写！！！
//        创建aidl实例时，如果让binder返回null，别的进程就拿不到数据碌
//        @Override
//        public IBinder asBinder() {
//            return null;
//        }
    };
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    Log.e(TAG,"收到新书"+msg.obj);
                    break;
            }
        }
    };
    @Override
    public void onDestroy() {
        if(mIBookManager !=null&&mIBookManager.asBinder().isBinderAlive()){
            try {
//                这里如果必然会解注册失败，因为传递的本进程对象到了别的进程会序列化成新对象，即解注册的不是原来的对象，因此失败
//                回到本质，这里的解注册是自己定义的方法，本质是在Service的list中判断有无该对象，有则remove
//                list判断有无对象是根据hashcode，即是否同一对象，所以会失败
                mIBookManager.unregisterListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}