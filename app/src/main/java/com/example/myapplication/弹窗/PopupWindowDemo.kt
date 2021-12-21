///*
//package com.example.myapplication.弹窗
//
//import android.app.Activity
//import android.graphics.drawable.ColorDrawable
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.MotionEvent
//import android.view.View
//import android.view.View.inflate
//import android.view.ViewTreeObserver
//import android.widget.PopupWindow
//import android.widget.RelativeLayout
//import com.example.myapplication.R
//
//class PopupWindowDemo : Activity(), View.OnClickListener {
//    private var mButton1: View? = null
//    private var mButton2: View? = null
//    private var mButton3: View? = null
//    private var mButton4: View? = null
//    private var mButton5: View? = null
//    private var mButton6: View? = null
//    private var mCurPopupWindow: PopupWindow? = null
//    protected override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_top_arrow_pos_window)
//        mButton1 = findViewById(R.id.buttion1)
//        mButton2 = findViewById(R.id.buttion2)
//        mButton3 = findViewById(R.id.buttion3)
//        mButton4 = findViewById(R.id.buttion4)
//        mButton5 = findViewById(R.id.buttion5)
//        mButton6 = findViewById(R.id.buttion6)
//        mButton1.setOnClickListener(this)
//        mButton2.setOnClickListener(this)
//        mButton3.setOnClickListener(this)
//        mButton4.setOnClickListener(this)
//        mButton5.setOnClickListener(this)
//        mButton6.setOnClickListener(this)
//    }
//
//    override fun onClick(v: View) {
//        val id: Int = v.getId()
//        when (id) {
//            R.id.buttion1 -> mCurPopupWindow =
//                showTipPopupWindow(mButton1, object : OnClickListener() {
//                    fun onClick(v: View?) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show()
//                    }
//                })
//            R.id.buttion2 -> mCurPopupWindow =
//                showTipPopupWindow(mButton2, object : OnClickListener() {
//                    fun onClick(v: View?) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show()
//                    }
//                })
//            R.id.buttion3 -> mCurPopupWindow =
//                showTipPopupWindow(mButton3, object : OnClickListener() {
//                    fun onClick(v: View?) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show()
//                    }
//                })
//            R.id.buttion4 -> mCurPopupWindow =
//                showTipPopupWindow(mButton4, object : OnClickListener() {
//                    fun onClick(v: View?) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show()
//                    }
//                })
//            R.id.buttion5 -> showTipPopupWindow(mButton5, object : OnClickListener() {
//                fun onClick(v: View?) {
//                    Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show()
//                }
//            })
//            R.id.buttion6 -> mCurPopupWindow =
//                showTipPopupWindow(mButton6, object : OnClickListener() {
//                    fun onClick(v: View?) {
//                        Toast.makeText(getBaseContext(), "点击到弹窗内容", Toast.LENGTH_SHORT).show()
//                    }
//                })
//        }
//    }
//
//    private fun showTipPopupWindow(anchorView: View, onClickListener: View.OnClickListener): PopupWindow {
//        val contentView: View = LayoutInflater.from(anchorView.getContext())
//        inflate(R.layout.popuw_content_top_arrow_layout, null)
//        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
//        // 创建PopupWindow时候指定高宽时showAsDropDown能够自适应
//        // 如果设置为wrap_content,showAsDropDown会认为下面空间一直很充足（我以认为这个Google的bug）
//        // 备注如果PopupWindow里面有ListView,ScrollView时，一定要动态设置PopupWindow的大小
//        val popupWindow = PopupWindow(
//            contentView,
//            contentView.getMeasuredWidth(), contentView.getMeasuredHeight(), false
//        )
//        contentView.setOnClickListener(object : View.OnClickListener() {
//            fun onClick(v: View?) {
//                popupWindow.dismiss()
//                onClickListener.onClick(v)
//            }
//        })
//        contentView.getViewTreeObserver()
//            .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener() {
//                fun onGlobalLayout() {
//                    // 自动调整箭头的位置
//                    autoAdjustArrowPos(popupWindow, contentView, anchorView)
//                    contentView.getViewTreeObserver().removeGlobalOnLayoutListener(this)
//                }
//            })
//        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
//        popupWindow.setBackgroundDrawable(ColorDrawable())
//        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
//        popupWindow.setOutsideTouchable(true)
//        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
//        popupWindow.setTouchable(true)
//        // true时，点击返回键先消失 PopupWindow
//        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
//        // false时PopupWindow不处理返回键
//        popupWindow.setFocusable(false)
//        popupWindow.setTouchInterceptor(object : View.OnTouchListener() {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                return false // 这里面拦截不到返回键
//            }
//        })
//        // 如果希望showAsDropDown方法能够在下面空间不足时自动在anchorView的上面弹出
//        // 必须在创建PopupWindow的时候指定高度，不能用wrap_content
//        popupWindow.showAsDropDown(anchorView)
//        return popupWindow
//    }
//
//    private fun autoAdjustArrowPos(popupWindow: PopupWindow, contentView: View, anchorView: View) {
//        val upArrow: View = contentView.findViewById(R.id.up_arrow)
//        val downArrow: View = contentView.findViewById(R.id.down_arrow)
//        val pos = IntArray(2)
//        contentView.getLocationOnScreen(pos)
//        val popLeftPos = pos[0]
//        anchorView.getLocationOnScreen(pos)
//        val anchorLeftPos = pos[0]
//        val arrowLeftMargin: Int =
//            anchorLeftPos - popLeftPos + anchorView.getWidth() / 2 - upArrow.getWidth() / 2
//        upArrow.setVisibility(if (popupWindow.isAboveAnchor()) View.INVISIBLE else View.VISIBLE)
//        downArrow.setVisibility(if (popupWindow.isAboveAnchor()) View.VISIBLE else View.INVISIBLE)
//        val upArrowParams: RelativeLayout.LayoutParams =
//            upArrow.getLayoutParams() as RelativeLayout.LayoutParams
//        upArrowParams.leftMargin = arrowLeftMargin
//        val downArrowParams: RelativeLayout.LayoutParams =
//            downArrow.getLayoutParams() as RelativeLayout.LayoutParams
//        downArrowParams.leftMargin = arrowLeftMargin
//    }
//
//    override fun onBackPressed() {
//        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
//            mCurPopupWindow.dismiss()
//        } else {
//            super.onBackPressed()
//        }
//    }
//}
//*/
