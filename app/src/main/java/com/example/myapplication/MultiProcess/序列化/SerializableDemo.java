package com.example.myapplication.MultiProcess.序列化;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
//java自带，简单，开销大，存储到设备和网络传输才推荐
public class SerializableDemo implements Serializable {
    //    uid会写入序列化后的文件，反序列化时会校验
    //    防止序列化与反序列化前后类发生变化
    //    transient标志不进入序列化
    private static transient final long serialVersionUID = 43134433141L;

    //    序列化
    private void x() throws IOException {
        SerializableDemo mSerializableDemo = new SerializableDemo();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("cache.txt"));
        oos.writeObject(mSerializableDemo);
        oos.close();
    }

    //    反序列化
    private void fx() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("cache.txt"));
//        恢复后不是同一个对象，但内容相同
        SerializableDemo mSerializableDemo = (SerializableDemo) ois.readObject();
        ois.close();
    }

}
