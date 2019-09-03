package com.alex.svgandroid.base.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import com.alex.svgandroid.R
import com.alex.svgandroid.utils.initTransparentStatusBar
import com.alex.svgandroid.utils.isDarkStyle
import com.alex.svgandroid.utils.setTitleBar
import com.alex.svgandroid.utils.setViewDrawable


/**
 * 透明状态栏基类
 * 使用 com.gyf.immersionbar 实现 Activity 的透明状态栏效果
 * 使用 androidx.appcompat.widget.Toolbar 作为标题栏
 */
abstract class BaseTransparentToolbarActivity : AutoDisposeActivity() {

  // 当前布局 ID
  abstract val layoutId: Int

  /**
   * 如果当前页面有自己的 Toolbar，则使用当前页面的，此时需要传递一个 Toolbar Id
   */
  open fun setToolbarLayoutId(): Int {
    return -1
  }

  /**
   * 设置当前页面的 Toolbar 样式（背景、文字等等），也可以在当前页面的 onCreate() 方法中设置
   * 专门写一个方法的主要目的是 Toolbar 的背景颜色需要在初始化 ImmersionBar 之前设置好，不然状态栏
   * 字体颜色不会改变
   */
  open fun setToolbarContent(toolbar: Toolbar) {}

  /**
   * 是否设置 Toolbar，大多数情况下页面都有 Toolbar，只用改
   * 变背景色和标题（使用 setToolbarContent(toolbar)）
   */
  open fun isSetToolbar(): Boolean {
    return true
  }

  private lateinit var mToolbar: Toolbar
  private lateinit var mToolbarBackImageView: ImageView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // 子布局
    val childrenView = LayoutInflater
      .from(this)
      .inflate(layoutId, null) as ViewGroup

    if (setToolbarLayoutId() == -1) {
      if (isSetToolbar()) {
        // 如果子布局要使用父布局中的通用 Toolbar，那么使用 LinearLayout 加入这两个布局
        // 父布局
        val contentView = RelativeLayout(this)

        mToolbar = LayoutInflater
          .from(this)
          .inflate(R.layout.layout_toolbar, contentView, false) as Toolbar
        mToolbarBackImageView = mToolbar.findViewById(R.id.toolbarBack)
        mToolbarBackImageView.setOnClickListener { finish() }
        setToolbarContent(mToolbar)

        val paramToolbar = RelativeLayout.LayoutParams(
          RelativeLayout.LayoutParams.MATCH_PARENT,
          RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        contentView.addView(mToolbar, paramToolbar)

        val paramContent = RelativeLayout.LayoutParams(
          RelativeLayout.LayoutParams.MATCH_PARENT,
          RelativeLayout.LayoutParams.MATCH_PARENT
        )
        paramContent.addRule(RelativeLayout.BELOW, mToolbar.id)
        contentView.addView(childrenView, paramContent)

        setContentView(contentView)
      } else {
        setContentView(childrenView)
      }
    } else {
      mToolbar = childrenView.findViewById(setToolbarLayoutId())
      setContentView(childrenView)
    }

    if (isSetToolbar()) {
      val toolbarBackgroundColor = (mToolbar.background as? ColorDrawable)?.color ?: -1
      val isDarkFont = isDarkStyle(toolbarBackgroundColor)
      if (setToolbarLayoutId() == -1) {
        // 根据不同状态栏颜色设置不同返回键图片（白色和黑色）
        mToolbarBackImageView.setImageResource(
          if (isDarkFont) R.drawable.toolbar_back
          else R.drawable.toolbar_back_white
        )
        // 设置返回键按压的背景，相对 Toolbar 背景设置一定的阴影
        mToolbarBackImageView.setViewDrawable(
          toolbarBackgroundColor,
          showContentDrawable = false, showMaskDrawable = false
        )
      }

      // 初始化 immersionbar，并设置状态栏字体颜色和 Toolbar
      initTransparentStatusBar(isDarkFont)
      setTitleBar(mToolbar)
    } else {
      initTransparentStatusBar()
    }
  }

}