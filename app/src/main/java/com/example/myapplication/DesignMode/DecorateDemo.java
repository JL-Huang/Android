package com.example.myapplication.DesignMode;

public class DecorateDemo {
    public static void main(String[] args) {
        Component component=new RealComponent();
        Decorator decorator=new RealDecorator(component);
        component.attack();
    }
}
abstract class Component{
    public abstract void attack();
}
class RealComponent extends Component{
    @Override
    public void attack() {
        System.out.println("fist");
    }
}
abstract class Decorator extends Component{
    private Component mComponent;

    public Decorator(Component component) {
        mComponent = component;
    }

    @Override
    public void attack() {
        mComponent.attack();
    }
}
class RealDecorator extends Decorator{

    public RealDecorator(Component component) {
        super(component);
    }
    public void decorate(){
        System.out.println("leg");
    }

    @Override
    public void attack() {
        super.attack();
        decorate();
    }
}
