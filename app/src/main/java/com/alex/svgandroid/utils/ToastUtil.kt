@file:Suppress("unused")

package com.alex.svgandroid.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

// 统一设置 Toast，如果需要可以设置自定义 Toast 样式


fun toast(context: Context?, content: String) {
  Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
}

fun toast(context: Context?, resId: Int) {
  Toast.makeText(context, context?.getString(resId), Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(content: String) {
  Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(content: String) {
  Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(resId: Int) {
  Toast.makeText(context, getString(resId), Toast.LENGTH_SHORT).show()
}

fun Activity.toast(resId: Int) {
  Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
}