package com.alex.svgandroid.base.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alex.svgandroid.utils.log

abstract class BaseLogFragment : Fragment() {

  val mCurrentClassName: String by lazy {  javaClass.simpleName }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    log(mCurrentClassName, "onAttach")
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    log(mCurrentClassName, "onActivityCreated")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    log(mCurrentClassName, "onCreate")
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    log(mCurrentClassName, "onCreateView")
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    log(mCurrentClassName, "onViewCreated")
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    log(mCurrentClassName, "onViewStateRestored")
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    when (newConfig.orientation) {
      Configuration.ORIENTATION_PORTRAIT ->
        log(mCurrentClassName, "onConfigurationChanged - portrait")
      Configuration.ORIENTATION_LANDSCAPE ->
        log(mCurrentClassName, "onConfigurationChanged - landscape")
    }
  }

  override fun onResume() {
    super.onResume()
    log(mCurrentClassName, "onResume")
  }

  override fun onPause() {
    super.onPause()
    log(mCurrentClassName, "onPause")
  }

  override fun onStart() {
    super.onStart()
    log(mCurrentClassName, "onStart")
  }

  override fun onStop() {
    super.onStop()
    log(mCurrentClassName, "onStop")
  }

  override fun onDestroy() {
    super.onDestroy()
    log(mCurrentClassName, "onDestroy")
  }

  override fun onDestroyView() {
    super.onDestroyView()
    log(mCurrentClassName, "onDestroyView")
  }

  override fun onDetach() {
    super.onDetach()
    log(mCurrentClassName, "onDetach")
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    log(mCurrentClassName, "onSaveInstanceState")
  }

  override fun onHiddenChanged(hidden: Boolean) {
    super.onHiddenChanged(hidden)
    log(mCurrentClassName, "onHiddenChanged $hidden")
  }

  override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
    super.onInflate(context, attrs, savedInstanceState)
    log(mCurrentClassName, "onInflate")
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    log(mCurrentClassName, "onActivityResult")
  }

  override fun onAttachFragment(childFragment: Fragment) {
    super.onAttachFragment(childFragment)
    log(mCurrentClassName, "onAttachFragment")
  }

}