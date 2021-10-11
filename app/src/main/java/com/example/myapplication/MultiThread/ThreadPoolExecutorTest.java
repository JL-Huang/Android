package com.example.myapplication.MultiThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {
//    corePoolSize核心线程数，核心线程即使闲置也会存活
//    maximumPoolSize最大线程数，活动线程超过这个数后面的会阻塞
//    keepAliveTime，非核心线程闲置最大时长，超过会被回收
//    unit，keepAliveTime，时间单位，枚举
//    workQueue，任务队列，excute提交的Runnable对象会存储在这里
//    threadFactory，工厂，创建线程用
//    handler，由于任务队列满了或其他导致无法执行任务的回调
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              ThreadFactory threadFactory,
//                              RejectedExecutionHandler handler)
}
