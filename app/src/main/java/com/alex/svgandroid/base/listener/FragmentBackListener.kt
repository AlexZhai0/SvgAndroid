package com.alex.svgandroid.base.listener

/**
 * 监听 Fragment 页面返回键
 */
interface FragmentBackListener {

  /**
   * 监听到返回键时要做的操作
   */
  fun onBackPressed(): Boolean
}