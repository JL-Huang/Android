package com.example.myapplication.MultiThread;

import android.os.AsyncTask;

import java.net.URL;

//三个泛型参数Params, Progress, Result
public class AsyncTaskTest extends AsyncTask<URL,Integer,Long> {

//    主线程执行，用来做线程开启前的准备操作
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
//    线程池中执行异步任务，数组是异步任务输入的参数，在此方法中通过publishProgress更新进度
//    publishProgress中调用onProgressUpdate，计算结果返回给onPostExecute
    @Override
    protected Long doInBackground(URL... urls) {
        int count=urls.length;
        long totalSize=0;
        for(int i=0;i<count;i++){
            totalSize+=Downloader.downloadFile(urls[i]);
            publishProgress((i/count)*100);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
    }
}
class Downloader{
    URL[] urls;
    public Downloader(URL... urls) {
        this.urls=urls;

    }

    public static long downloadFile(URL url) {
        return 0;
    }
}
//上面的类通过new AsyncTaskTest().excute(url1,url2,url3)调用
