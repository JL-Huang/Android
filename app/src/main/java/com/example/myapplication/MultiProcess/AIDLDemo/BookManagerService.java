package com.example.myapplication.MultiProcess.AIDLDemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookManagerService extends Service {
    private static final String TAG = "BMS";
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
//    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList=new RemoteCallbackList<>();
    //    创建一个Binder，直接用aidl中的方法STub进行实例化
    private Binder mBinder = new IBookManager.Stub() {
        //        重写接口方法
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
//            将接口中的对象与当前服务端的对象联系起来
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if(!mListenerList.contains(listener)) {
//                mListenerList.add(listener);
//                Log.e(TAG,"注册的监听器"+listener);
//            }
//            Log.e(TAG,"注册后监听器数量:"+mListenerList.size());

//            注意，RemoteCallbackList集合调用成员的方法是通过直接调用方法，输入成员作为参数
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if(mListenerList.contains(listener)) {
//                mListenerList.remove(listener);
//            } else{
//
//            }
//            Log.e(TAG,"解注册后监听器数量:"+mListenerList.size());
            mListenerList.unregister(listener);
        }
    };

    public BookManagerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        返回这个完成了aidl功能的binder
        return mBinder;
    }

    @Override
    public void onCreate() {
        mBookList.add(new Book(0, "Android"));
        mBookList.add(new Book(1, "iOS"));
        new Thread(new BookManagerServiceThread()).start();
    }
    private void onNewBookArrived(Book book){
        mBookList.add(book);
        Log.e(TAG,"新书到了，现在有"+mBookList.size());
//        for (int i=0;i<mListenerList.size();i++){
//        通过广播的形式获得集合成员个数
        for (int i=0;i<mListenerList.beginBroadcast();i++){
//            Log.e(TAG,String.valueOf(mListenerList.size()));
//            IOnNewBookArrivedListener listener=mListenerList.get(i);
            IOnNewBookArrivedListener listener=mListenerList.getBroadcastItem(i);
            Log.e(TAG,String.valueOf(listener));
            try {
//                这里特别关键，通过调用接口方法得到别的进程的listener，调用了别的进程的listener的方法
                if(listener!=null)
                listener.onNewBookArrived(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
//        别忘记关闭广播
        mListenerList.finishBroadcast();
    }
//    这是为了方便演示，实际上在Service进程不需要也尽量避免开启线程
//    因为运行在别的进程的Service本身就可以执行耗时操作
    private class BookManagerServiceThread implements Runnable{

        @Override
        public void run() {
            while(!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId=mBookList.size()+1;
                Book newBook=new Book(bookId,"NewBook"+bookId);
                onNewBookArrived(newBook);
            }
        }
    }

}