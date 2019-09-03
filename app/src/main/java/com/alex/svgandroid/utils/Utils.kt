package com.alex.svgandroid.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources.getSystem
import android.view.View
import android.view.WindowManager
import com.alex.svgandroid.R
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


// 10.dp
val Number.dp: Int
  get() = (toInt() * getSystem().displayMetrics.density).toInt()

// 屏幕宽高
val width get() = getSystem().displayMetrics.widthPixels
val height get() = getSystem().displayMetrics.heightPixels

/**
 * View 的扩展函数，处理不可连续点击事件。
 *
 * callback 被接收的 lambda
 */
@SuppressLint("CheckResult")
fun View.onNoDoubleClick(callback: () -> Unit) {
  clicks()
    .throttleFirst(1L, TimeUnit.SECONDS) // 1s内执行一次操作
    // .debounce(1L, TimeUnit.SECONDS) // 1s后执行操作
    .subscribeOn(AndroidSchedulers.mainThread())
    .subscribe {
      callback()
    }
}

// NFC 是否可用
@Suppress("unused")
fun nfcVilible(context: Context?): Boolean {
//  (context?.getSystemService(Context.NFC_SERVICE)
//      as? NfcManager)?.defaultAdapter?.isEnabled
  return context?.packageManager?.hasSystemFeature(
    PackageManager.FEATURE_NFC
  ) ?: false
}

fun setFullScreen(activity: Activity?) {
  activity?.window?.setFlags(
    WindowManager.LayoutParams.FLAG_FULLSCREEN,
    WindowManager.LayoutParams.FLAG_FULLSCREEN)
}
fun setNormalScreen(activity: Activity?) {
  val attrs = activity?.window?.attributes
  attrs?.flags = attrs?.flags?.and(WindowManager.LayoutParams.FLAG_FULLSCREEN.inv())
  activity?.window?.attributes = attrs
}

fun Activity.finishThisActivity() {
  finish()
  overridePendingTransition(R.anim.pop_enter_to_right, R.anim.pop_exit_to_right)
}

fun Activity.startAnotherActivity(intent: Intent) {
  startActivity(intent)
  overridePendingTransition(R.anim.enter_to_left, R.anim.exit_to_left)
}