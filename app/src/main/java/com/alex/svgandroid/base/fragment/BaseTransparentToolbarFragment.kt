package com.alex.svgandroid.base.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import com.alex.svgandroid.R
import com.alex.svgandroid.utils.*

/**
 * 透明状态栏基类
 * 使用 com.gyf.immersionbar 实现 Activity 的透明状态栏效果
 * 使用 androidx.appcompat.widget.Toolbar 作为标题栏
 */
abstract class BaseTransparentToolbarFragment : BaseContentViewFragment() {

  /**
   * 如果当前页面特殊，有自己的 Toolbar，则需要传递一个 Toolbar Id
   */
  open fun setToolbarLayoutId(): Int {
    return -1
  }

  /**
   * 是否设置 Toolbar，大多数情况下页面都有 Toolbar，只用改
   * 变背景色和标题（使用 setToolbarContent(toolbar)）
   */
  open fun isSetToolbar(): Boolean {
    return true
  }

  /**
   * 设置当前页面的 Toolbar 样式（背景、文字等）
   */
  open fun setToolbarContent(toolbar: Toolbar) {}

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    log(mCurrentClassName, "onCreateView")
    // 子布局
    val childrenView = LayoutInflater
      .from(context)
      .inflate(layoutId, container, false)

    lateinit var mToolbar: Toolbar

    if (setToolbarLayoutId() == -1) {
      if (isSetToolbar()) {
        // 如果子布局要使用父布局中的通用 Toolbar，那么使用 LinearLayout 加入这两个布局
        // 父布局
        val contentView = RelativeLayout(context)

        mToolbar = LayoutInflater
          .from(context)
          .inflate(R.layout.layout_toolbar, contentView, false) as Toolbar

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

        setToolbarImageView(mToolbar)
        return contentView
      } else {
        initTransparentStatusBar()
        return childrenView
      }
    } else {
      mToolbar = childrenView.findViewById(setToolbarLayoutId())
      setToolbarImageView(mToolbar)
      return childrenView
    }
  }

  private fun setToolbarImageView(toolbar: Toolbar) {
    val toolbarBackgroundColor = (toolbar.background as? ColorDrawable)?.color ?: -1
    val isDarkFont = isDarkStyle(toolbarBackgroundColor)
    if (setToolbarLayoutId() == -1) {
      val mToolbarBackImageView = toolbar.findViewById<ImageView>(R.id.toolbarBack)
      mToolbarBackImageView.setOnClickListener {
        if (!NavHostFragment.findNavController(this).popBackStack()) {
          activity?.finishThisActivity()
        }
      }

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
    setTitleBar(toolbar)
  }

}