package com.example.myapplication.RxJavaDemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        rxjava1();
    }
    private void rxjava1(){
        Subscriber subscriber=new Subscriber() {
//        onCompleted:事件队列完结。RxJava不仅把每个事件单独处理，其还会把它们看作一个队列。
//        当不会再有新的 onNext发出时，需要触发 onCompleted()方法作为完成标志。
            @Override
            public void onCompleted() {

            }
//        onError:事件队列异常。在事件处理过程中出现异常时，onError()会被触发，同时队列自动终止，不允许再有事件发出。
            @Override
            public void onError(Throwable e) {

            }
//        onNext:普通的事件。将要处理的事件添加到事件队列中。
            @Override
            public void onNext(Object o) {
                System.out.println("========"+o);
            }
        };
//        创建被观察者方法一，直接创建,然后手动调用onnext
        Observable observable1=Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
//                手动调用subscriber的onnext方法
                subscriber.onNext("hhh");
            }
        });
//        创建被观察者方法二，just，just可输入多个参数，会自动调用onnext,将just的参数输入
        Observable observable2=Observable.just("hhh");
//        创建被观察者方法三，from，同样会自动调用onnext
        String[] words={"hhh"};
        Observable observable3=Observable.from(words);

//        被观察者订阅订阅者，会自动调用subscriber的onstart和observable的call
        observable1.subscribe(subscriber);
//        可以用action代替subscriber，调用action的call
        Action1<String> action1=new Action1<String>() {
            @Override
            public void call(String s) {
            }
        };
//        被观察者订阅活动
        observable1.subscribe(action1);
//        interval 创建一个按固定时间间隔发射整数序列的Observable，相当于定时器
        Observable.interval(3, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                System.out.println(aLong.intValue());
            }
        });
//        range 创建发射指定范围的整数序列的Observable，可以拿来替代for循环，发射一个范围内的有序整数序列。
//        第一个参数是起始值，并且不小于0;第二个参数为终值，左闭右开。
        Observable.range(0,5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer.intValue());//0,1,2,3,4
            }
        });
//        repeat 创建一个N次重复发射特定数据的Observable
        Observable.range(0,3).repeat(2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer.intValue());//0,1,2,0,1,2
            }
        });
//        map 通过指定一个Func对象，将Observable转换为一个新的Observable对象并发射，观察者将收 到新的Observable处理。
//        最后打印http://blog.csdn.net/itachi85
//        过程是先将itachi85合成为http:blog.csdn.net/itachi85，然后调用subscribe，将合成的字段作为参数输入call方法
        final String Host="https://baidu.com";
        Observable.just("itachi85").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return Host+s;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
//        flatMap 将Observable发射的数据集合变换为Observable集合，然后将这些Observable发射的数据 平坦化地放进一个单独的 Observable。
//        cast 强制将 Observable 发射的所有数据转换为指定类型。
        List<String> list=new ArrayList<>();
        list.add("itachi85");
        list.add("itachi86");
        list.add("itachi87");
//        先from构造一个被观察者，然后flatMap将list转化为Observable，转化的具体操作是通过just构建，
//        还要将Observable中 的数据转换为String类型，最后订阅观察者，就能将参数输入活动中
        Observable.from(list).flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                return Observable.just(Host+s);
            }
        }).cast(String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
//        concatMap 解决了flatMap交叉问题，提供了一种能够把发射的值连续在一起的函数，而不是合并它们，使用方法和flatMap类似
//        flatMaplterable 可以将数据包装成Iterable，在Iterable中可以对数据进行处理
//        注意转化成Iterable的时候，需要指定泛型，就是just输入参数的类型
        Observable.just(1,2,3).flatMapIterable(new Func1<Integer, Iterable<Integer>>() {
            @Override
            public Iterable<Integer> call(Integer integer) {
                List<Integer> list=new ArrayList<>();
                list.add(integer+1);
                return list;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        });
//        buffer 将源Observable变换为一个新的Observable，这个新的Observable每次发射一组列表值而不 是一个一个发射
        Observable.just(1,2,3).buffer(3).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                for(Integer i:integers){
                    System.out.println(i);
                }
                System.out.println("========");
            }
        });
//        groupBy 用于分组元素，将源Observable变换成一个发射Observables的新Observable (分组后的)。
//        它们中的每一个新Observable都发射一组指定的数据
    }
}