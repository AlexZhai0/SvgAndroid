package com.alex.svgandroid.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import androidx.core.graphics.ColorUtils

/**
 * 设置点击时的 Drawable 效果
 *
 * @param color 一个正常显示的颜色，默认白色
 * @param radius 设置按钮的圆角
 * @param radiusLeftTop 矩形左上角的圆角
 * @param radiusLeftBottom 矩形左下角的圆角
 * @param radiusRightTop 矩形右上角的圆角
 * @param radiusRightBottom 矩形右下角的圆角
 * @param pressedColor 按下后的颜色，如果没有则根据正常颜色计算出按下颜色
 * @param showContentDrawable 是否设置按下之前状态，true 有边界的正常按下状态，且会显示初始背景色
 * @param showMaskDrawable 是否设置波纹 mask，false 且 showContentDrawable 也是 false 时无边界
 * @param showCommonSelector 使用5.0之前的按下效果（无波纹）
 */
@SuppressLint("ObsoleteSdkInt")
@Suppress("SameParameterValue", "DEPRECATION")
fun View.setViewDrawable(
  color: Int = Color.parseColor("#FFFFFF"),
  radius: Float = 0f,
  radiusLeftTop: Float = 0f,
  radiusLeftBottom: Float = 0f,
  radiusRightTop: Float = 0f,
  radiusRightBottom: Float = 0f,
  pressedColor: Int = 0,
  showContentDrawable: Boolean = true,
  showMaskDrawable: Boolean = true,
  showCommonSelector: Boolean = false
) {
  // 点击后的颜色
  val pressColor = if (pressedColor == 0) shallowColorWithAlpha(color) else pressedColor

  val drawable: Drawable
  val roundRectShape: RoundRectShape

  val gradientDrawableNormal = GradientDrawable()
  gradientDrawableNormal.setColor(color)
  if (radius != 0f) {
    gradientDrawableNormal.cornerRadius = radius
    val outRadius = floatArrayOf(
      radius, radius, radius, radius,
      radius, radius, radius, radius)
    roundRectShape = RoundRectShape(outRadius, null, null)
  } else {
    val floatArray = floatArrayOf(
      radiusLeftTop, radiusLeftTop,
      radiusLeftBottom, radiusLeftBottom,
      radiusRightTop, radiusRightTop,
      radiusRightBottom, radiusRightBottom)
    gradientDrawableNormal.cornerRadii = floatArray
    roundRectShape = RoundRectShape(floatArray, null, null)
  }

  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !showCommonSelector) {
    val maskDrawable = ShapeDrawable()
    maskDrawable.shape = roundRectShape
    // 点击有水波纹效果
    drawable = RippleDrawable(
      ColorStateList.valueOf(pressColor),
      if (showContentDrawable) gradientDrawableNormal else null,
      if (showMaskDrawable) maskDrawable else null
    )
  } else {
    // 正常点击效果
    val gradientDrawablePress = GradientDrawable()
    gradientDrawablePress.setColor(pressColor)
    if (radius != 0f) {
      gradientDrawablePress.cornerRadius = radius
    } else {
      gradientDrawablePress.cornerRadii = floatArrayOf(
        radiusLeftTop, radiusLeftTop,
        radiusLeftBottom, radiusLeftBottom,
        radiusRightTop, radiusRightTop,
        radiusRightBottom, radiusRightBottom)
    }
    drawable = StateListDrawable()
    drawable.addState(intArrayOf(android.R.attr.state_pressed), gradientDrawablePress)
    if (showContentDrawable) {
      drawable.addState(intArrayOf(), gradientDrawableNormal)
    }
  }
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    background = drawable
  } else {
    setBackgroundDrawable(drawable)
  }
}

/**
 * 对 color 颜色的色彩添加阴影，并加入透明度
 *
 * @param color 要改变的颜色
 * @param shallowRatio 阴影相对于 color 的比例
 * @param shallowNum 当 color 是黑色时，计算的数值不能小于 shallowNum
 * @return 添加了阴影和透明度的色值
 */
private fun shallowColorWithAlpha(
  color: Int,
  shallowRatio: Float = 0.9f,
  shallowNum: Int = 50
): Int {
  val alpha = Color.alpha(color)
  var red = Color.red(color)
  var green = Color.green(color)
  var blue = Color.blue(color)

  if (red <= shallowNum && green <= shallowNum && blue <= shallowNum) {
    red = shallowNum
    green = shallowNum
    blue = shallowNum
  } else {
    red = (red * shallowRatio).toInt()
    green = (green * shallowRatio).toInt()
    blue = (blue * shallowRatio).toInt()
  }
  return Color.argb(alpha, red, green, blue)
}

/**
 * 计算 color 是浅色还是深色
 */
fun isDarkStyle(color: Int?): Boolean {
  val outHsl = FloatArray(3)
  val blackMaxLightness = 0.05f
  val whiteMinLightness = 0.95f
  ColorUtils.colorToHSL(color ?: -1, outHsl)
  return when {
    outHsl[2] <= blackMaxLightness -> false
    outHsl[2] >= whiteMinLightness -> true
    else -> false
  }
}