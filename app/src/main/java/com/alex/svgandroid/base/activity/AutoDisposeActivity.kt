package com.alex.svgandroid.base.activity

import androidx.lifecycle.Lifecycle
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 * 使用 com.uber.autodispose 防止 RxJava 内存泄漏
 */
abstract class AutoDisposeActivity : BaseLogActivity() {

  protected val scopeProvider: AndroidLifecycleScopeProvider by lazy {
    AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)
  }

}