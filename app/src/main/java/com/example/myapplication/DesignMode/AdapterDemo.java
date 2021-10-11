package com.example.myapplication.DesignMode;
//适配器模式
public class AdapterDemo {
    public interface MusicPlayer{
        public void play(String format);
    }
    public interface VedioPlayer{
        public void playWMV(String format);
        public void playRMVB(String format);
    }
    public class MP3 implements MusicPlayer{

        @Override
        public void play(String format) {

        }
    }
    public static class MP4 implements VedioPlayer{

        @Override
        public void playWMV(String format) {

        }

        @Override
        public void playRMVB(String format) {

        }
    }
//    该适配器可以实现用音乐播放器放视频
    public static class MusicAdapter1 implements MusicPlayer{
//        这里可以进一步改写，新建对象时可以用接口的不同实现类
    VedioPlayer mp4=new MP4();
    @Override
    public void play(String format) {
        mp4.playRMVB("RMVB");
        mp4.playWMV("MP4");
    }
}
//    这是另一种写法，关键是适配器实现当前接口，在接口方法中实现别的类的方法
    public static class MusicAdapter2 extends MP4 implements MusicPlayer{

    @Override
    public void play(String format) {
        this.playRMVB(format);
        this.playWMV(format);
    }
}
//    该类集成了适配器，可以实现同时播放视频与音乐
}
   class AudioPlayer {
    public static void main(String[] args) {
    AdapterDemo.MusicAdapter1 adapter1=new AdapterDemo.MusicAdapter1();
    AdapterDemo.MusicAdapter2 adapter2=new AdapterDemo.MusicAdapter2();
//    实现用音乐播放器实现类，通过play方法实现视频播放
        adapter1.play("RMVB");
    }

}
