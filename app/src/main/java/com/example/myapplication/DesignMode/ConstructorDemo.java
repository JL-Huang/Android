package com.example.myapplication.DesignMode;

//建造者模式：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
//使用场景:
//当创建复杂对象的算法应该独立于该对象的组成部分以及它们的装配方式时。
//相同的方法，不同的执行顺序，产生不同的事件结果时。
//多个部件或零件都可以被装配到一个对象中，但是产生的运行结果又不相同时。
public class ConstructorDemo {
    public static void main(String[] args) {
//        使用建造者模式进行组装
        Builder builder = new RealBuilder();
        Director director = new Director(builder);
        ComputerConstructed computerConstructed = director.constructComputer("10400", "H510", "金士顿16g");
    }
}
//组装后的产品，成员变量为各个元件
class ComputerConstructed {
    public String cpu;
    public String mainBoard;
    public String ram;

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setMainBoard(String mainBoard) {
        this.mainBoard = mainBoard;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }
}
//抽象组装类，规范产品组装
abstract class Builder {
    public abstract void buildCpu(String cpu);

    public abstract void buildMainBoard(String mainBoard);

    public abstract void buildRam(String ram);

    public abstract ComputerConstructed create();
}
//具体组装类，内部实现通过给定的元件组装产品
class RealBuilder extends Builder {
    private ComputerConstructed mComputerConstructed = new ComputerConstructed();

    @Override
    public void buildCpu(String cpu) {
        mComputerConstructed.setCpu(cpu);
    }

    @Override
    public void buildMainBoard(String mainBoard) {
        mComputerConstructed.setMainBoard(mainBoard);
    }

    @Override
    public void buildRam(String ram) {
        mComputerConstructed.setRam(ram);
    }

    @Override
    public ComputerConstructed create() {
        return mComputerConstructed;
    }
}
//导演类，负责安排组装顺序，然后调用Builder进行组装
class Director {
    Builder mBuilder = null;

    public Director(Builder builder) {
        mBuilder = builder;
    }

    public ComputerConstructed constructComputer(String cpu, String mainBoard, String ram) {
        this.mBuilder.buildMainBoard(mainBoard);
        this.mBuilder.buildCpu(cpu);
        this.mBuilder.buildRam(ram);
        return mBuilder.create();
    }
}
