package com.alex.svgandroid.base.listener

import android.content.Context
import android.view.OrientationEventListener

class MyOrientationChangeHelper(
  context: Context?,
  private val listener: MyOrientationEventListener
) : OrientationEventListener(context) {

  private var mIsOrientation0 = false
  private var mIsOrientation90 = false
  private var mIsOrientation180 = false
  private var mIsOrientation270 = false

  override fun onOrientationChanged(orientation: Int) {
    if (orientation >= 340 || orientation <= 20) {// 0度
      mIsOrientation90 = false
      mIsOrientation180 = false
      mIsOrientation270 = false
      if (!mIsOrientation0) {
        mIsOrientation0 = true
        listener.currentOrientation(0)
      }
    } else if (orientation in 70..110) {// 90度
      mIsOrientation0 = false
      mIsOrientation180 = false
      mIsOrientation270 = false
      if (!mIsOrientation90) {
        mIsOrientation90 = true
        listener.currentOrientation(90)
      }
    } else if (orientation in 160..200) {// 180度
      mIsOrientation0 = false
      mIsOrientation90 = false
      mIsOrientation270 = false
      if (!mIsOrientation180) {
        mIsOrientation180 = true
        listener.currentOrientation(180)
      }
    } else if (orientation in 250..290) {// 270度
      mIsOrientation0 = false
      mIsOrientation90 = false
      mIsOrientation180 = false
      if (!mIsOrientation270) {
        mIsOrientation270 = true
        listener.currentOrientation(270)
      }
    } else {
      return
    }
    if (orientation == ORIENTATION_UNKNOWN) {//手机平放时，检测不到有效的角度
      return
    }
  }
}