package com.alex.svgandroid.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseContentViewFragment : BaseLifecycleMethodFragment() {

  abstract val layoutId: Int

  private var mView: View? = null

  lateinit var mContext : Context

  override fun onAttach(context: Context) {
    super.onAttach(context)
    mContext = context
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mView = LayoutInflater.from(context).inflate(layoutId, container, false)
    return mView
  }

  override fun onDestroyView() {
    super.onDestroyView()
    mView = null
  }

}