package com.example.applicationreachtest.utils

import android.content.ContextWrapper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView


/**
 * @Author: Dilip
 * @Date: 17/10/21
 */
class BinderAdapter {

    companion object {
        @BindingAdapter("mutableVisibility")
        @JvmStatic
        fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
            val parentActivity: AppCompatActivity? = view.getParentActivity()
            if (parentActivity != null && visibility != null) {
                visibility.observe(
                    parentActivity,
                    Observer { value -> view.visibility = value ?: View.VISIBLE })
            }
        }

        @BindingAdapter("mutableText")
        @JvmStatic
        fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
            val parentActivity: AppCompatActivity? = view.getParentActivity()
            if (parentActivity != null && text != null) {
                text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
            }
        }

        @BindingAdapter("adapter")
        @JvmStatic
        fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
            view.adapter = adapter
        }

        fun View.getParentActivity(): AppCompatActivity? {
            var context = this.context
            while (context is ContextWrapper) {
                if (context is AppCompatActivity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }
    }
}