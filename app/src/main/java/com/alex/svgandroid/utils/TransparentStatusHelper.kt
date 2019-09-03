@file:Suppress("unused", "UNUSED_PARAMETER")

package com.alex.svgandroid.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.ImmersionBar

/**
 * 禁止某些特殊类型手机适配状态栏
 */
private fun isForbiddenTypePhone(): Boolean {
  return when {
    isOPPOSystem() -> true
    else -> false
  }
}

/**
 * 适配特殊类型手机
 */
private fun Activity.initSpecialTypePhone(isDarkFont: Boolean) {
  // 其他机型适配...
  // setOPPOToolbarColor(isDarkFont)
}

fun Activity.initTransparentStatusBar() {
  if (isForbiddenTypePhone()) return
  ImmersionBar.with(this).init()
}

fun Fragment.initTransparentStatusBar() {
  if (isForbiddenTypePhone()) return
  ImmersionBar.with(this).init()
}

fun Activity.initTransparentStatusBar(isDarkFont: Boolean) {
  if (isForbiddenTypePhone()) return
  ImmersionBar.with(this).statusBarDarkFont(isDarkFont).init()
  initSpecialTypePhone(isDarkFont)
}

fun Fragment.initTransparentStatusBar(isDarkFont: Boolean) {
  if (isForbiddenTypePhone()) return
  ImmersionBar.with(this).statusBarDarkFont(isDarkFont).init()
  initSpecialTypePhone(isDarkFont)
}

fun Fragment.setTitleBarMarginTop(view: View) {
  if (isForbiddenTypePhone()) return
  ImmersionBar.setTitleBarMarginTop(this, view)
}

fun Activity.setTitleBar(view: View) {
  if (isForbiddenTypePhone()) return
  ImmersionBar.setTitleBar(this, view)
}

fun Fragment.setTitleBar(view: View) {
  if (isForbiddenTypePhone()) return
  ImmersionBar.setTitleBar(this, view)
}

private fun Fragment.initSpecialTypePhone(isDarkFont: Boolean) {
  activity?.initSpecialTypePhone(isDarkFont)
}

private fun isOPPOSystem(): Boolean {
  return (Build.MANUFACTURER == "OPPO"
      && Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
}

private fun Activity.setOPPOToolbarColor(isDarkFont: Boolean) {
  if (Build.MANUFACTURER == "OPPO"
    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    && Build.VERSION.SDK_INT < Build.VERSION_CODES.M
  ) {
    val window = window
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    val systemInt = 0x00000010
    var visibility = window.decorView.systemUiVisibility
    visibility = if (isDarkFont) {
      visibility or systemInt
    } else {
      visibility and systemInt.inv()
    }
    window.decorView.systemUiVisibility = visibility
  }
}
