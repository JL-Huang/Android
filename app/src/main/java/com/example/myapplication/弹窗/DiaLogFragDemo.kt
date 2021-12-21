package com.example.myapplication.弹窗

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R

class DiaLogFragDemo() : DialogFragment() {
    lateinit var editText: EditText
    lateinit var cancel: ImageView
    lateinit var submit: Button
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity!!)
        dialog.setCanceledOnTouchOutside(false)
        initView(dialog)
        initEvent()
        return dialog
    }

    fun initView(dialog: Dialog) {
        val contentView = LayoutInflater.from(getContext())
            .inflate(R.layout.project_manage_layput_create_project_frag, null);
        editText = contentView.findViewById<EditText>(R.id.edit_text_name)
        cancel = contentView.findViewById<ImageView>(R.id.cancel_create_project);
        submit = contentView.findViewById<Button>(R.id.start_collect);
        dialog.setContentView(contentView)
    }


    private fun initEvent() {
        submit.setOnClickListener { v ->
            val trim: String = editText.getText().toString().trim()
            if (!TextUtils.isEmpty(trim)) {
            } else {
                Toast.makeText(context, "输入为空", Toast.LENGTH_SHORT).show()
            }
        }
        cancel.setOnClickListener { v -> dialog!!.dismiss() }


    }

    fun showKeyboard(view: View) {
        val imm: InputMethodManager =
            view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm != null) {
            view.requestFocus()
            imm.showSoftInput(view, 0)
        }
    }

    fun hideSoftkeyboard() {
        try {
            (activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(
                    activity!!.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
        } catch (e: NullPointerException) {
        }
    }
}