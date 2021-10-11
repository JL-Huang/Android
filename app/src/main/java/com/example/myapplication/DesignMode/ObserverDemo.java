package com.example.myapplication.DesignMode;

import java.util.ArrayList;
import java.util.List;
//观察者模式
public class ObserverDemo {
    public interface MyObserver{
        public void update(String message);
    }
//    观察者，update方法是每次被观察者有新内容时被观察者进行调用以便对观察者进行通知
    public class User implements  MyObserver{
        private String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public void update(String message) {
            System.out.println(name+message);
        }
    }
//    抽象被观察者
    public interface Subject{
        public void attach(MyObserver observer);
        public void detach(MyObserver observer);
        public void notify(String message);
    }
//    具体被观察者，内部维护一个list引进观察者，内部状态发生改变时，给所有注册过的观察者发送通知。
    public class SubscriptSubject implements Subject{
    private List<MyObserver> list=new ArrayList<>();
        @Override
        public void attach(MyObserver observer) {
            list.add(observer);
        }

        @Override
        public void detach(MyObserver observer) {
            list.remove(observer);
        }

        @Override
        public void notify(String message) {
            for(MyObserver each:list){
                each.update(message);
            }
        }
    }
}
