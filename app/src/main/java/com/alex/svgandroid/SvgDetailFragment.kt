package com.alex.svgandroid

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.alex.svgandroid.base.fragment.BaseFragment
import com.alex.svgandroid.base.listener.BackHandlerHelper
import com.alex.svgandroid.base.listener.FragmentBackListener
import com.alex.svgandroid.base.listener.MyOrientationChangeHelper
import com.alex.svgandroid.base.listener.MyOrientationEventListener
import com.alex.svgandroid.SvgListFragment.Companion.FILE_NAME
import com.alex.svgandroid.SvgListFragment.Companion.FILE_PATH
import com.alex.svgandroid.utils.setFullScreen
import com.alex.svgandroid.utils.setNormalScreen
import kotlinx.android.synthetic.main.fragment_svg.*


/**
 * 使用 WebView 打开 SVG 文件
 *
 * 1、查找手机上所有的 SVG 文件，并生成列表
 * 2、点击列表中的某个 SVG 文件就用 Fragment 打开
 * 3、尝试使用 ViewModel，旋转屏幕可以查看
 * 4、优化 WebView 显示
 */
class SvgDetailFragment : BaseFragment(),
  FragmentBackListener,
  MyOrientationEventListener {

  override val layoutId = R.layout.fragment_svg

  private var isLandscape: Boolean = false

  private lateinit var mToolbarTitle: TextView
  private lateinit var mToolbar: Toolbar

  private val mOrientationEvent by lazy {
    MyOrientationChangeHelper(context, this)
  }

  override fun setToolbarContent(toolbar: Toolbar) {
    super.setToolbarContent(toolbar)
    toolbar.setBackgroundColor(ContextCompat.getColor(context!!, R.color.white))
    mToolbarTitle = toolbar.findViewById(R.id.toolbarTitle)
    mToolbar = toolbar
  }

  override fun landscapeListener(isLan: Boolean) {
    super.landscapeListener(isLan)
    isLandscape = isLan
    if (isLan) {
      mToolbar.visibility = View.GONE
      setFullScreen(activity)
    } else {
      mToolbar.visibility = View.VISIBLE
      setNormalScreen(activity)
    }
  }

  @SuppressLint("SetJavaScriptEnabled")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val webSettings = webViewSVG.settings

    webSettings.javaScriptEnabled = true

    webSettings.loadWithOverviewMode = true // 默认不缩放
    webSettings.useWideViewPort = true // 默认不缩放

    webSettings.setSupportZoom(true) // 支持缩放
    webSettings.builtInZoomControls = true // 支持缩放
    webSettings.displayZoomControls = false // 隐藏缩放按钮

    webViewSVG.loadUrl(arguments?.getString(FILE_PATH))

    mToolbarTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
    mToolbarTitle.text = arguments?.getString(FILE_NAME)
  }

  override fun onBackPressed(): Boolean {
    // 做返回之前要做的事情
    return if (isLandscape) {
      mToolbar.visibility = View.VISIBLE
      setNormalScreen(activity)
      // 如果当前是横屏就强制改为竖屏
      activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
      true
    } else {
      // 当确认没有子Fragmnt时可以直接return
      BackHandlerHelper.handleBackPress(this)
    }

  }

  override fun currentOrientation(angle: Int) {
    if (angle == 0 || angle == 180) {
      if (!isLandscape &&
        activity?.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
      ) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
      }
    }
  }

  override fun onResume() {
    super.onResume()
    mOrientationEvent.enable()
  }

  override fun onPause() {
    super.onPause()
    mOrientationEvent.disable()
  }

}



