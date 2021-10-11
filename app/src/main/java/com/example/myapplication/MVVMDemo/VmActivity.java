//package com.example.myapplication.MVVMDemo;
//
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.databinding.DataBindingUtil;
//
//import com.example.myapplication.R;
//import com.example.myapplication.databinding.ActivityVmBinding;
//
////MVVM是MVP的升级版，取消了presenter与view的绑定
//public class VmActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_vm);
////        创建一个ViewModel实例
//        NumViewModel numViewModel = new NumViewModel();
////        与xml文件绑定
//        ActivityVmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_vm);
////        NumVM是xmk文件的key
//        binding.setNumVM(numViewModel);//View与ViewModel绑定
//    }
//}