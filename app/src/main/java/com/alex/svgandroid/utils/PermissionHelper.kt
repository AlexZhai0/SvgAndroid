package com.alex.svgandroid.utils

import android.content.Context
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission

@Suppress("HasPlatformType")
object CheckPermission {
  val storage = Permission.Group.STORAGE
}

fun checkPermission(
  context: Context?,
  permissions: Array<String>,
  permissionAllowedCallback: ((List<String>) -> Unit)? = null,
  permissionDeniedCallback: ((List<String>) -> Unit)? = null
) {
  AndPermission.with(context)
    .runtime()
    .permission(permissions)
    .onGranted { it1 ->
      permissionAllowedCallback?.let { it11 -> it11(it1) }
    }
    .onDenied { it2 ->
      permissionDeniedCallback?.let { it22 -> it22(it2) }
    }
    .start()
}