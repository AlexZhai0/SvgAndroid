package com.alex.svgandroid.base.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.alex.svgandroid.utils.log

abstract class BaseLogActivity : FragmentActivity() {

  val mCurrentClassName: String by lazy {  javaClass.simpleName }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    log(mCurrentClassName, "onCreate")
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    log(mCurrentClassName, "onNewIntent")
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    log(mCurrentClassName, "onAttachedToWindow")
  }

  override fun onRestart() {
    super.onRestart()
    log(mCurrentClassName, "onRestart")
  }

  override fun onStart() {
    super.onStart()
    log(mCurrentClassName, "onStart")
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    log(mCurrentClassName, "onSaveInstanceState")
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    super.onRestoreInstanceState(savedInstanceState)
    log(mCurrentClassName, "onRestoreInstanceState")
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

  override fun onStop() {
    super.onStop()
    log(mCurrentClassName, "onStop")
  }

  override fun onDestroy() {
    super.onDestroy()
    log(mCurrentClassName, "onDestroy")
  }

}