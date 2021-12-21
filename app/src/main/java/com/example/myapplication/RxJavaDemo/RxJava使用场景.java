package com.example.myapplication.RxJavaDemo;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJava使用场景 {
    public static void main(String[] args) {
        RxJava使用场景 rxJava=new RxJava使用场景();
        rxJava.postAsynHttp("10.33.192.69");
    }
    private Observable<String> getObservable(final String ip){
        Observable observable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                OkHttpClient okHttpClient=new OkHttpClient();
                RequestBody requestBody=new FormBody.Builder().add("ip",ip).build();
                Request request=new Request.Builder().url("https://baidu.com").post(requestBody).build();
                Call call=okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        subscriber.onError(new Exception("error"));
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        subscriber.onNext(response.body().string());
                        subscriber.onCompleted();
                    }
                });
            }
        });
        return observable;
    }
    private void postAsynHttp(String size){
        getObservable(size).subscribeOn(Schedulers.io()).observeOn(Schedulers.immediate()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });
    }
}
