package com.example.myapplication.RxJavaDemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TimeUtils;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

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
//        还要将Observable中的数据转换为String类型，最后订阅观察者，就能将参数输入活动中
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
                return list;//返回一个Iterable子类对象
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);//2，3，4
            }
        });
//        buffer 将源Observable变换为一个新的Observable，这个新的Observable每次发射一组列表值而不是一个一个发射
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
        class University{
            String name;
            char level;

            public University(String name, Character level) {
                this.name = name;
                this.level = level;
            }
        }
        University u1=new University("u1",'A');
        University u2=new University("u2",'S');
        University u3=new University("u3",'A');
//        输入一个GroupedObservable给观察者，GroupedObservable指定的两个泛型前者是分类的key，后者是被分类对象
        Observable<GroupedObservable<Character,University>> groupedObservable=Observable.just(u1,u2,u3).groupBy(new Func1<University, Character>() {
            @Override
            public Character call(University university) {
//                返回分类依据
                return university.level;
            }
        });
//        concat将各组依次发送
        Observable.concat(groupedObservable).subscribe(new Action1<University>() {
            @Override
            public void call(University university) {
                System.out.println(university.name+university.level);//u1A,u3A,u2S
            }
        });
//        filter 对源Observable产生的结果自定义规则进行过滤，只有满足条件的结果才会提交给订阅者
        Observable.just(1,2,3).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
//                返回布尔类型的过滤规则
                return integer>2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);//3
            }
        });
//        elementAt 用来返回指定位置的数据。和它类似的有elementAtOrDefault(int，T)，其可以允许默认值。
        Observable.just(1,2,3).elementAt(2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);//3
            }
        });
//        distinct 去重，其只允许还没有发射过的数据项通过。和它类似的还有distinctUntilChanged，用来去掉连续重复的数据。
        Observable.just(1,2,3,4,5,5,5,1).distinct().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);//1,2,3,4,5
            }
        });
//        skip 将源Observable发射的数据过滤掉前n项
//        take 只取前n项;另外还有skipLast和 takeLast操作符，则是从后面进行过滤操作
        Observable.just(1,2,3,4).skip(2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        });
//        ignoreElements 忽略所有源Observable产生的结果，只把Observable的onCompleted和onError事件通知给订阅者
        Observable.just(1,2,3).ignoreElements().subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");//输出onCompleted
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");//不执行
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext");//不执行
            }
        });
//        throttleFirst 定期发射这个时间段里源Observable发射的第一个数据
//        throttleFirst操作符默认在computation 调度器上执行(关于调度器后面会讲到)。
//        和 throttleFirst 操作符类似的有sample操作符，它会定时地发射源Observable最近发射的数据
        Observable.create(new Observable.OnSubscribe<Integer>() {
//            注意这里subscriber的泛型不能直接用Integer,必须是用super
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i=0;i<10;i++){
                    subscriber.onNext(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).throttleFirst(200,TimeUnit.SECONDS).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer); //0,2,4,6,8
            }
        });
//        startWith 在源Observable发射的数据前面插上一些数据
        Observable.just(3,4,5).startWith(1,2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);//1,2,3,4,5
            }
        });
//        merge 将多个Observable合并到一个Observable中进行发射，merge可能会让合并的Observable发射 的数据交错。
        Observable.merge(Observable.just(1,2,3).subscribeOn(Schedulers.io()),Observable.just(4,5,6)).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);//4,5,6,1,2,3
            }
        });
//        concat 将多个 Obserbavle 发射的数据进行合并发射。
//        concat 严格按照顺序发射数据，前一个Observable没发射 完成是不会发射后一个Observable的数据的。
        Observable.concat(Observable.just(1,2,3).subscribeOn(Schedulers.io()),Observable.just(4,5,6)).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);//1,2,3，4，5，6
            }
        });
//        zip 合并两个或者多个Observable发射出的数据项，根据指定的函数变换它们，并发射一个新值。
        Observable.zip(Observable.just(1, 2, 3).subscribeOn(Schedulers.io()), Observable.just('a', 'b', 'c'), new Func2<Integer, Character, String>() {
            @Override
            public String call(Integer integer, Character character) {
                return integer+String.valueOf(character);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);//1a,2b,3c
            }
        });
//        combineLastest 当两个Observable中的任何一个发射了数据时，使用一个函数结合每个Observable发射的最近数据项，
//        并且基于这个函数的结果发射数据。combineLatest 操作符和zip有些类似。
//        zip操作符作用于最近未打包的两个Observable，只有当原始的Observable中的每一个都发射了一条数据时zip才发射数据;
//        而 combineLatest 操作符作用于最近发射的数据项，在原始Observable中的任意一个发射了数据时发射一条数据。
//        简单来说就是，zip是两个输入都更新才更新输出，如果只更新一个另一个没更新则不会输出
//        combineLatest是一个输入更新就会取所有输入最新值进行输出
        Observable.combineLatest(Observable.just(1, 2, 3).subscribeOn(Schedulers.io()), Observable.just('a', 'b', 'c'), new Func2<Integer, Character, String>() {
            @Override
            public String call(Integer integer, Character character) {
                return integer+String.valueOf(character);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);//可能是3a,3b,3c
            }
        });
//        delay 让原始Observable在发射每项数据之前都暂停一段指定的时间段。
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                Long currentTime=System.currentTimeMillis()/1000;
                subscriber.onNext(currentTime);
            }
        }).delay(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                System.out.println(System.currentTimeMillis()/1000-aLong);//2
            }
        });
//        Do 为原始Observable的生命周期事件注册一个回调，当Observable的某个事件发生时就会调用这些回调。
//        doOnEach:为 Observable注册这样一个回调，当Observable每发射一项数据时就会调用它一次，包括 onNext、onError和 onCompleted。
//        doOnNext:只有执行onNext的时候会被调用。
//        doOnSubscribe:当观察者订阅Observable时就会被调用。
//        doOnUnsubscribe:当观察者取消订阅Observable时就会被调用;Observable通过onError或者 onCompleted结束时，会取消订阅所有的Subscriber。
//        doOnCompleted:当Observable 正常终止调用onCompleted时会被调用。
//        doOnError:当Observable 异常终止调用onError时会被调用。
//        doOnTerminate:当Observable 终止(无论是正常终止还是异常终止)之前会被调用。
//        finallyDo:当Observable 终止(无论是正常终止还是异常终止)之后会被调用。
        Observable.just(1,2).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("call"+integer);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }
        });//call1,1,call2,2
//        subscribeOn  指定Observable自身在哪个线程上运行。如果Observable需要执行耗时操作，一般可以让其在新开的一个子线程上运行。
//        observerOn 指定Observer所运行的线程，也就是发射出的数据 在哪个线程上使用。一般情况下会指定在主线程中运行，这样就可以修改UI。
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                System.out.println("Observable"+Thread.currentThread().getName());
                subscriber.onNext(1);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println("Observer"+Thread.currentThread().getName());
            }
        });//结果是先打印RxNewThreadScheduler-2（Observable线程）后打印main（Observer线程）
//        timeout 如果原始 Observable 过了指定的一段时长没有发射任何数据，timeout会以一个onError通知终止这个Observable，或者继续执行一个备用的Observable。
//        timeout有很多变体，这里介绍其中的一种: timeout(long，TimeUnit，Observable)，它在超时时会切换到使用一个你指定的备用的Observable，而不是发送错误通知。
//        它默认在computation调度器上执行
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i=0;i<4;i++){
                    try {
                        Thread.sleep(i*100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
            }//如果Observable在200ms这段时长没有发射数据，就会切换到Observable.just(10，11),只执行一次
        }).timeout(200,TimeUnit.MILLISECONDS,Observable.just(10,11)).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        });//0,1,2,10,11
//        catch 拦截原始Observable的onError通知，将它替换为其他数据项或数据序列，让产生的 Observable能够正常终止或者根本不终止。RxJava将catch实现为以下 3个不同的操作符。
//        onErrorReturn:Observable遇到错误时返回原有Observable行为的备用Observable，备用Observable会忽略原有Observable的onError调用，不会将错误传递给观察者。作为替代，它会发射一个特殊的项并调用观察 者的onCompleted方法。
//        onErrorResumeNext:Observable遇到错误时返回原有Observable行为的备用Observable，备用 Observable会忽略原有Observable的onError调用，不会将错误传递给观察者。作为替代，它会发射备用 Observable的数据。
//        onExceptionResumeNext:它和onErrorResumeNext类似。不同的是，如果onError收到的Throwable不是 一个Exception，它会将错误传递给观察者的onError方法，不会使用备用的Observable。
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i=0;i<4;i++){
                    if(i>2){
                        subscriber.onError(new Throwable(">2"));
                    }
                    subscriber.onNext(i);
                }
            }
        }).onErrorReturn(new Func1<Throwable, Integer>() {
            @Override
            public Integer call(Throwable throwable) {
                return 6;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {

            }
        });//0,1,2,6(忽略原有Observable的onError调用)

//        retry 不会将原始Observable的onError通知传递给观察者，它会订阅这个Observable，再给它一次机会无错误地完成其数据序列。
//        retry总是传递onNext通知给观察者，由于重新订阅，这可能会造成数据项重复。RxJava 中的实现为retry和retryWhen。
//        拿retry(long)来举例，它指定最多重新订阅的次数。如 果次数超了，它不会尝试再次订阅，而会把最新的一个onError通知传递给自己的观察者
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i=0;i<3;i++){
                    if(i==1){
                        subscriber.onError(new Throwable("==1"));
                    }else{
                        subscriber.onNext(i);
                    }
                }
            }
//            重新订阅次数为2，在i=0的时候会调用上面代码注释1处的onNext方法。
//            此外重试的这两次同样 会调用onNext方法，这样一共会调用3次onNext方法，最后才会调用onError方法
        }).retry(2).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println(integer);
            }
        });//0,0,0,==1
//        all 根据一个函数对源Observable发射的所有数据进行判断，最终返回的结果就是这个判断结果。
//        这个函数使用发射的数据作为参数，内部判断所有的数据是否满足我们定义好的判断条件。
//        如果全部都满足则返回true，否则就返回false。
        Observable.just(1,2,3).all(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
//                true不会发送，但当遇到违反此条件的发射时，它将发出 false 并调用onComplete()。
                return integer<3;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                System.out.println(aBoolean);
            }
        });//1,2,false

//        contains 操作符用来判断源 Observable 所发射的数据是否包含某一个数据。
//        如果包含该数据，会返回 true;如果源Observable已经结束了却还没有发射这个数据，则返回false。
//        isEmpty操作符用来判断源 Observable 是否发射过数据。
//        如果发射过该数据，就会返回 false;如果源Observable已经结束了却还没有发 射这个数据，则返回true。
        Observable.just(1,2,3).contains(1).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                System.out.println(aBoolean);
            }
        });//true
//        amb 对于给定两个或多个 Observable，它只发射首先发射数据或通知的那个Observable的所有数据。
        Observable.amb(Observable.just(1,2,3).delay(2,TimeUnit.SECONDS),Observable.just(4,5,6)).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        });//4,5,6
//        defaultIfEmpty 发射来自原始Observable的数据。如果原始Observable没有发射数据(onNext)，就发射一个默认数据
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onCompleted();
            }
        }).defaultIfEmpty(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(integer);
            }
        });//3
//        toList 将发射多项数据且为每一项数据调用onNext方法的Observable发射的多项数据组合成一个List，然后调用一次onNext方法传递整个列表。
//        与flatMap有点像，flatMap是将list转换成一个个observable
        Observable.just(1,2,3).toList().subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                System.out.println(integers.toArray().toString());
            }
        });//[1,2,3]
//        toSortedList 类似于toList操作符;不同的是，它会对产生的列表排序，默认是自然升序。
//        如果发 射的数据项没有实现Comparable接口，会抛出一个异常。
//        当然，若发射的数据项没有实现Comparable接口， 可以使用toSortedList(Func2)变体，其传递的函数参数Func2会作用于比较两个数据项。

//        toMap 收集原始Observable发射的所有数据项到一个Map(默认是HashMap)，然后发射这个 Map。
//        可以提供一个用于生成Map的key的函数，也可以提供一个函数转换数据项到Map存储的值(默认 数据项本身就是值)。
        Observable.just(u1,u2,u3).toMap(new Func1<University, String>() {
            @Override
            public String call(University university) {
                return university.name;
            }
        }).subscribe(new Action1<Map<String, University>>() {
            @Override
            public void call(Map<String, University> stringUniversityMap) {
                System.out.println(stringUniversityMap.toString());
            }
        });
    }
}