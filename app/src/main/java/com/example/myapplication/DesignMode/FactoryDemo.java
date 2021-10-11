package com.example.myapplication.DesignMode;

//工厂模式
public class FactoryDemo {
    public static void main(String[] args) {
        ComputerFactory computerFactory = new RealComputerFactory();
        LenovoComputer lenovoComputer = computerFactory.createComputer(LenovoComputer.class);
        lenovoComputer.start();
        HPComputer hpComputer = computerFactory.createComputer(HPComputer.class);
        hpComputer.start();
    }
}
abstract class Computer {
    public abstract void start();
}

//    各种产品
class LenovoComputer extends Computer {

    @Override
    public void start() {
        System.out.println("联想电脑启动");
    }
}

class HPComputer extends Computer {

    @Override
    public void start() {
        System.out.println("惠普电脑启动");
    }
}

//    抽象工厂，通过反射来创造产品
abstract class ComputerFactory {
    public abstract <T extends Computer> T createComputer(Class<T> clz);
}

//    反射的优点在于，有新产品时不需要修改工厂类，只需要在调用时传入新产品的Class对象
class RealComputerFactory extends ComputerFactory {

    @Override
    public <T extends Computer> T createComputer(Class<T> clz) {
        Computer computer = null;
        String className = clz.getName();
        try {
            computer = (Computer) Class.forName(className).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) computer;
    }
}
