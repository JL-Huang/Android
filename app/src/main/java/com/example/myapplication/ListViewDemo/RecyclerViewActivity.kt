package com.example.myapplication.ListViewDemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_recycler_view.*
import java.util.*

class RecyclerViewActivity : AppCompatActivity() {
    private var list= ArrayList<FruitItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initFruitList()
//        将list与recyclerview绑定后，layoutManager控制的是元素的排列
        val layoutManager=LinearLayoutManager(this)
        layoutManager.orientation=LinearLayoutManager.HORIZONTAL
        fruit_recycler.layoutManager=layoutManager
        fruit_recycler.adapter=FruitAdapter(list)
    }
    class FruitItem(var id: Int, var name: String)

    fun initFruitList() {
        list.add(FruitItem(1, "apple"))
        list.add(FruitItem(2, "orange"))
        list.add(FruitItem(3, "pear"))
    }
}
class FruitAdapter(val fruitlist:List<RecyclerViewActivity.FruitItem>):RecyclerView.Adapter<FruitAdapter.ViewHolder>(){
//    ViewHolder的成员变量实际完成的是getview中子控件findviewbyid的操作
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val fruit_id:TextView=view.findViewById(R.id.fruit_id)
        val fruit_name:TextView=view.findViewById(R.id.fruit_name)
    }
//    onCreateViewHolder完成的是ViewHolder的创建，即渲染子元素xml，并返回渲染后的ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.fruit_item,parent,false)
        return ViewHolder(view)
    }
//    onBindViewHolder完成的是子元素中各个控件的填充，position就是对应list的下标
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit=fruitlist[position]
        holder.fruit_id.setText(fruit.id.toString())
        holder.fruit_id.setOnClickListener {
//            val show = Toast.makeText(instance, "按下了${fruit.name}", Toast.LENGTH_SHORT).show()
        }
        holder.fruit_name.setText(fruit.name)
    }
//    getItemCount是数据源长度
    override fun getItemCount(): Int {
        return fruitlist.size
    }
}