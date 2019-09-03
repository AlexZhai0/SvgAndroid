@file:Suppress("ConstantConditionIf")

package com.alex.svgandroid.utils

import androidx.fragment.app.Fragment
import timber.log.Timber

// 优点：把简单的 Log 统一管理，方便测试，可以随时更换工具类，例如使用 Log
// 缺点：必须输入当前页面的 javaClass.simpleName
// 建议：如果几个页面统一调试就是用下面的方法，如果单个页面调试就直接用 Timber



// 是否显示 Log，在测试数据的时候打开
const val isShowLog = true
// Log 中的 tag 后缀，方便在几个页面测试时过滤 Log
const val tagName = "--Alex"

fun log(context: Fragment?, msg: String) {
  if (!isShowLog) return
  Timber.tag(context?.javaClass?.simpleName).d(msg)
  // Timber.d("cs1: %s , cs2 '%s'.", "nr1", "nr2")
}

fun log(msg: String) {
  if (!isShowLog) return
  Timber.d(msg)
  // Timber.d("cs1: %s , cs2 '%s'.", "nr1", "nr2")
}

fun log(tag: String, msg: String) {
  if (!isShowLog) return
  Timber.tag(tag + tagName).d(msg)
}