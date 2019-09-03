package com.alex.svgandroid.base.listener

/**
 * 监听屏幕旋转角度
 */
interface MyOrientationEventListener {

  /**
   * 当前屏幕旋转角度
   *
   * @param angle 只有四个角度：0度、90度、180度、270度
   */
  fun currentOrientation(angle: Int)
}