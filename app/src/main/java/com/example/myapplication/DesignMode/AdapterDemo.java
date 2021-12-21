package com.example.myapplication.DesignMode;
//适配器模式
public class AdapterDemo {
    public interface MusicPlayer{
        void playMP3();
    }
    public interface PhotoPlayer {
        void playJPG();
    }
    public class MP3 implements MusicPlayer{

        @Override
        public void playMP3() {

        }
    }
    public static class Album implements PhotoPlayer {
        @Override
        public void playJPG() {
        }
    }
    /*    这是另一种写法，关键是适配器实现当前接口，在接口方法中实现别的类的方法
    public static class Adapter extends Album implements MusicPlayer{

    @Override
    public void play(String format) {
        this.play(format);
    }
}
    该类集成了适配器，可以实现同时播放视频与音乐*/
    public static class Phone implements MusicPlayer,PhotoPlayer{
        @Override
        public void playMP3() {
            this.playJPG();
        }
        @Override
        public void playJPG() {
        }
    }
//    该适配器可以实现用音乐播放器放视频
    public static class Adapter implements MusicPlayer{
    PhotoPlayer album;
    public Adapter(PhotoPlayer album) {
        this.album = album;
    }
    @Override
    public void playMP3() {
        album.playJPG();
    }

}
}
   class AudioPlayer {
    public static void main(String[] args) {
    AdapterDemo.MusicPlayer mp3              =new AdapterDemo.Adapter(new AdapterDemo.Album());
//    实现用音乐播放器通过playMP3方法实现图片播放
        mp3.playMP3();
    }

}
