package com.alex.svgandroid.base.fragment

import androidx.lifecycle.Lifecycle
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

abstract class AutoDisposeFragment : BaseLogFragment() {

  protected val scopeProvider: AndroidLifecycleScopeProvider by lazy {
    AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)
  }

}