package com.example.myapplication.MultiProcess.序列化;

import android.os.Parcel;
import android.os.Parcelable;
//android专有，开销小，效率高
public class ParcelableDemo implements Parcelable {

//    从序列化后的对象还原数据
    protected ParcelableDemo(Parcel in) {
    }

    public static final Creator<ParcelableDemo> CREATOR = new Creator<ParcelableDemo>() {
        @Override
//        从序列化后的对象还原数据
        public ParcelableDemo createFromParcel(Parcel in) {
            return new ParcelableDemo(in);
        }

        @Override
//        创建指定长度的原始对象数组
        public ParcelableDemo[] newArray(int size) {
            return new ParcelableDemo[size];
        }
    };

    @Override
//    描述当前对象内容，1表示含有文件描述符，一般为0
    public int describeContents() {
        return 0;
    }

    @Override
//    将当前对象写入序列化结构，flag为1标记需要当前对象需要作为返回值，不能立即释放，一般为0
    public void writeToParcel(Parcel dest, int flags) {
    }
}
