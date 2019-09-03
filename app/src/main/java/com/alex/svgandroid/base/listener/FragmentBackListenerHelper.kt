package com.alex.svgandroid.base.listener

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment

class BackHandlerHelper {

  companion object {
    /**
     * 将back事件分发给 FragmentManager 中管理的子Fragment，如果
     * 该 FragmentManager 中的所有Fragment都没有处理back事件，则
     * 尝试 FragmentManager.popBackStack()
     *
     * @param fragmentManager 传入当前 Activity 或者 Fragment 中的 FragmentManager
     * @return 如果处理了back键则返回 true
     */
    private fun handleBackPress(fragmentManager: FragmentManager): Boolean {
      val fragments = fragmentManager.fragments
      for (i in fragments.indices.reversed()) {
        val child = fragments[i]

        // 区分 Android Jetpack 中的 Navigation
        if (child is NavHostFragment) {
          val childFragments = child.childFragmentManager.fragments
          childFragments.forEach { fragment ->
            if (fragment is FragmentBackListener) {
              if (isFragmentBackHandled(fragment)) {
                return true
              }
            }
          }
        }

        if (isFragmentBackHandled(child)) {
          return true
        }
      }
      if (fragmentManager.backStackEntryCount > 0) {
        fragmentManager.popBackStack()
        return true
      }
      return false
    }

    fun handleBackPress(fragment: Fragment): Boolean {
      return handleBackPress(fragment.childFragmentManager)
    }

    fun handleBackPress(fragmentActivity: FragmentActivity): Boolean {
      return handleBackPress(
        fragmentActivity.supportFragmentManager
      )
    }

    /**
     * 判断Fragment是否处理了Back键
     *
     * @return 如果处理了back键则返回 true
     */
    private fun isFragmentBackHandled(fragment: Fragment?): Boolean {
      return (fragment != null
          && fragment.isVisible
          // && fragment.userVisibleHint //for ViewPager
          && fragment is FragmentBackListener
          && (fragment as FragmentBackListener).onBackPressed())
    }
  }
}
