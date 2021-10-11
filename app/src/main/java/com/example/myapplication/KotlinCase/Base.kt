package com.example.myapplication

import java.io.BufferedReader
import java.lang.Exception
import java.lang.NullPointerException
import java.lang.NumberFormatException
import java.util.HashMap

//    主函数构造需要参数a
//    加了open才能被继承
open class Base(a: Int,b:Boolean,c:String) {
//    在主构造函数增加函数体
    init {
        print("hhh")
    }
    //    成员变量b，var必须制定setter和getter
    var b: Boolean
        get() {
            TODO()
        }
        set(value) {

        }
    val c:Int=1;
    //    新建对象
    var d: Base = Base(1,true,"ddd")
}
interface study{
    fun read();
//    可以有默认实现，默认实现的方法可以不被重写
    fun write(){
        print("write")
    }
}
//必须调用父类的构造方法，可通过自身构造方法传入参数
//:表示继承
//,表示实现接口
class BaseSon(p:Int,q:Boolean,r:String,s:Char):Base(3,q,r),study{
//    次构造函数，函数有两个参数，次构造函数必须调用或间接调用主构造函数，参数传递路径是由输入参数传递到调用参数
    constructor(t:Int,u:Boolean):this(0,u,"hhh",'s')
//    间接调用主构造函数
    constructor():this(1,true)

    override fun read() {
        TODO("Not yet implemented")
    }
}
//    枚举,不加val或var不能在枚举中的函数引用
enum class Color(var red: Int, val green: Int, val blue: Int) {
    RED(255, 0, 0),
    GREEN(0, 255, 0);

    fun rgb_compute(): Int {
        return red * 256 * 256 + green * 256 + blue
    }
}

// Kotlin 的基本数值类型包括 Byte、Short、Int、Long、Float、Double 等。
// 不同于 Java 的是，字符不属于数值类型，是一个独立的数据类型。
fun sum(a: Int, b: Int): Int {
    return a + b;
}

// 三个等号 === 表示比较对象地址，两个 == 表示比较两个值大小
// 同样是-128到127规则不一样
fun compare() {
    var a: Int = 128;
    var a_one: Int? = a;
    var a_two: Int? = a;
    println("==比较" + (a == a).toString())
    println("===比较" + (a === a).toString())
    println("==比较" + (a_one == a_two).toString())
    println("===比较" + (a_one === a_two).toString())
}

//较小的类型不能隐式转换为较大的类型
//Double	64
//Float	    32
//Long	    64
//Int	    32
//Short	    16
//Byte	    8

fun convert() {
    var a: Byte = 1;
//    var b: Int = a; //错误
    var b: Int = a.toInt();
    var c: Int = 1;
//    var d: Byte = c; //错误
    var d: Byte = c.toByte();
}

//循环语句
fun circle_operation() {
    for (a in 10 until 20 step 2) {
        print(a)
    }
    val tentoone = 10 downTo 1;
    for (a in tentoone) {
        print(a)
    }
}

//条件语句
//不同于Java，kotlin的if语句可以有返回值
fun if_operation() {
    var a = if (10 > 1) {
        print("yes")
        10
    } else {
        print("no")
        20
    }
}

//分支语句
fun when_operation1() {
    var x: Int = 1
    when (x) {
        0, 1 -> print("yes")
        2 -> print("no")
        else -> print("others")
    }
    when {
        x == 0 -> print("yes")
        x == 2 -> print("no")
        else -> print("others")
    }
}

fun when_operation2(num: Int) = when {
    num in 1..10 -> {
//        print(num)
        20
    }
    else -> {
//        print(num-1)
        10
    }
}

fun when_operation3(num: Any) = when (num) {
    is String -> {
        print("num is String")
    }
    else -> {
        print("num is not String")
    }
}

fun exception_operation(reader: BufferedReader) {
    try {
        var num: Int? = Integer.parseInt(reader.readLine())
        if (num == null) {
            throw NullPointerException("num is null")
        }

    } catch (e: NumberFormatException) {
        null
    } finally {
        reader.close()
    }
}

//Kotlin 中的 Char 不能直接和数字操作，Char 必需是单引号 ' 包含起来
fun char_operation() {
    var c: Char = '9'
//    println(c==0)//错误
    println(c.code - '0'.code)
}

fun array_opertion() {
//    多种初始化方式
    var a = arrayOf(1, 2, 3)
//    可变与不可变集合
    val p= listOf<Int>(1,2,3)
    val q= mutableListOf<Int>(1,2,3)
//    第一个参数数组长度，第二个参数是一个函数
//    kotlin可以以lambda表达式的形式将函数作为变量
//    ->前是输入参数，->后的表达式为函数返回值
//    该变量也有类型，() -> Unit是无参无返回值，(T,A) -> R是T,A类型参数R类型返回值
    var b = Array(3, { i: Int -> (i * 2) })
//    i是下标，作为lamda输入参数，因此b[1]输出2
    println(b[1])
//    其他类型数组
    var d: IntArray = intArrayOf(1, 2, 3)
//    set,get,size方法
    print(d.get(2))
}
fun map_operation(){
    val map=HashMap<String,String>();
    map["apple"]="apple"
    map.put("orange","orange")
    for((name1,name2)in map){
        print(name1+name2)
    }
    val map_2= mapOf<String,String>("peach" to "peach")

}
fun string_operation() {
    var text: String = " d dd";
//    逐个字符打印
    for (c: Char in text) {
//        print(c)
    }
//    print(text.trim())
//    模版表达式
    var a = 100;
    var s1 = "a=$a"
    var s2 = "$s1.length=${s1.length}"
    var s3 = "${'$'}"
    print(s3)
}

fun main() {
//    compare()
//    convert()
//    array_opertion()
//    char_operation()
//    string_operation()
//    circle_operation()
    print(when_operation2(2))
}