package com.example.myapplication.DesignMode;
//代理模式
//注意代理模式和适配器模式很像，但不同的是：
//适配器模式的适配器类中引入的是别的接口，而代理模式始终是一个接口
//代理模式是一个对象不适宜引用另一个对象，因此引入一个代理中介
//适配器模式是一个类不能直接用接口B的方法又不好直接实现接口B，因通过适配器引入接口B实现类并调用其方法，使得接口兼容
public class ProxyDemo {
    public static void main(String[] args) {
        ISubject subject=new RealSubject();
        ISubject proxy=new Proxy(subject);
        proxy.buy();
    }
}
//一个接口
interface ISubject {
    void buy();
}
//接口的实现类一：真实主题
class RealSubject implements ISubject {
    @Override
    public void buy() {
        System.out.println("买");
    }
}
//接口的实现类二：代理者
class Proxy implements ISubject{
//    代理者通过构造方法将真实主题传入，并在实现接口方法时调用真实主题的实现
    private ISubject mISubject;
    public Proxy(ISubject subject){
        mISubject=subject;
    }
    @Override
    public void buy() {
        mISubject.buy();
    }
}