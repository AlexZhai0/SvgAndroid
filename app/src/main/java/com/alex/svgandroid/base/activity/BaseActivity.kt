package com.alex.svgandroid.base.activity

import com.alex.svgandroid.base.activity.BaseTransparentToolbarActivity
import com.alex.svgandroid.base.listener.BackHandlerHelper


/**
 * 透明状态栏、网络基类
 */
abstract class BaseActivity : BaseTransparentToolbarActivity() {

  /**
   * 让 Fragment 捕捉返回键
   */
  override fun onBackPressed() {
    if (!BackHandlerHelper.handleBackPress(this)) {
      super.onBackPressed()
    }
  }

}