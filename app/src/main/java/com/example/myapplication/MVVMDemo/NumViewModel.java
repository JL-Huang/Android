//package com.example.myapplication.MVVMDemo;
//
//import android.view.View;
//
//import androidx.databinding.BaseObservable;
//import androidx.databinding.Bindable;
//
//import com.example.myapplication.BR;
//
////ViewModel负责业务逻辑处理，并且数据有更新直接通知View去更改。
////ViewModel对UI的更新不再依附于View
//// 在presenter中，需要绑定一个view然后调用view的接口方法改变ui
////而在ViewModel中，UI的更新直接通过与该组件的绑定完成
//public class NumViewModel extends BaseObservable {
//    private String num;
//
//    private final NumModel mNumVM;
//
//    public NumViewModel() {
//        mNumVM = new NumModel();
//    }
//
//    @Bindable
//    public String getNum() {
//        return num;
//    }
//
//    public void setNum(String num) {
//        this.num = num;
//        notifyPropertyChanged(BR.num);//更新UI
//    }
////逻辑也交给ViewModel处理，包括事件监听与处理
////这里的点击事件也是直接与view中的按钮绑定
//    public void onClickAdd(View view) {//点击事件处理
//        mNumVM.add(new NumModel.ModelCallback() {//相关逻辑处理，这里直接交给Model层
//            @Override
//            public void onSuccess(int num) {
//                setNum(num + "");//成功，更新数据
//            }
//
//            @Override
//            public void onFailed(String text) {
//                setNum(text);//失败，更新数据
//            }
//        });
//    }
//}
