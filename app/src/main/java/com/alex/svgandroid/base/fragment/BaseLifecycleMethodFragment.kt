package com.alex.svgandroid.base.fragment

import android.content.res.Configuration
import com.alex.svgandroid.base.fragment.AutoDisposeFragment

abstract class BaseLifecycleMethodFragment : AutoDisposeFragment() {


  /**
   * 页面可见时，每次切换进入该页面只执行一次
   * 场景：每次进入该页面要做的操作，比如添加监听、埋点等。
   */
  open fun executeEnterInto() {}

  /**
   * 页面不可见时，每次从这个页面出去只执行一次
   * 场景：每次从该页面出去时要做的操作，比如移除监听、埋点等。
   */
  open fun executeOut() {}

  /**
   * 横屏监听
   *
   * @param isLan true 时是横屏，false 时是竖屏
   */
  open fun landscapeListener(isLan: Boolean) {}


  override fun onResume() {
    super.onResume()
    executeEnterIntoOnce(true)
  }

  override fun onHiddenChanged(hidden: Boolean) {
    super.onHiddenChanged(hidden)
    executeEnterIntoOnce(!hidden)
    executeOutOnce(hidden)
  }

  private fun executeEnterIntoOnce(isEnterInto: Boolean) {
    if (isEnterInto) {
      executeEnterInto()
    }
  }

  private fun executeOutOnce(isOut: Boolean) {
    if (isOut) {
      executeOut()
    }
  }

  override fun onPause() {
    super.onPause()
    executeOutOnce(true)
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    when (newConfig.orientation) {
      Configuration.ORIENTATION_PORTRAIT ->
        landscapeListener(false)
      Configuration.ORIENTATION_LANDSCAPE ->
        landscapeListener(true)
    }
  }

}