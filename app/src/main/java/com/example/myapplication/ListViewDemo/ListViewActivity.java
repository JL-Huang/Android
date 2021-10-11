package com.example.myapplication.ListViewDemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {
    List<FruitItem> list=new ArrayList<FruitItem>();
    ListView fruit_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        fruit_list=findViewById(R.id.fruit_list);
        initFruitList();
//        传入子控件xml和实体list
        FruitAdapter adapter=new FruitAdapter(this,R.layout.fruit_item,list);
        fruit_list.setAdapter(adapter);
    }
//    创建一个List并往里填充内容
    void initFruitList(){
        list.add(new FruitItem(1,"apple"));
        list.add(new FruitItem(2,"orange"));

    }
//    创建一个类，该类就是要显示的每一个元素
    class FruitItem {
        int id;
        String name;

        public FruitItem(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
//    创建一个适配器，该适配器做的是传入子控件xml与list，把元素类和itemview及list连接起来
    class FruitAdapter extends ArrayAdapter<FruitItem> {
        Context context;
        int resource;
        List<FruitItem> objects;

        public FruitAdapter(@NonNull Context context, int resource, @NonNull List<FruitItem> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            this.objects = objects;
        }

        @NonNull
        @Override
//        子选项被滑动到屏幕时调用
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            渲染传入的xml
            View view = LayoutInflater.from(context).inflate(resource, parent, false);
//            初始化xml中的各个控件，注意view.
            TextView fruit_id=view.findViewById(R.id.fruit_id);
            TextView fruit_name=view.findViewById(R.id.fruit_name);
//            通过position可以从list中拿到数据
            FruitItem fruit=getItem(position);
            Log.e("TAG",fruit_name==null?"空":"不是空");
            if(fruit!=null){
                fruit_id.setText(String.valueOf(fruit.id));
                fruit_name.setText(fruit.name);
            }
            return view;
        }
    }
}