package com.chc.ebook.utils

import android.view.Window
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun showStatusBar(insetsController: WindowInsetsControllerCompat) = insetsController.show(
    WindowInsetsCompat.Type.statusBars()
)

fun hideStatusBar(insetsController: WindowInsetsControllerCompat) = insetsController.hide(
    WindowInsetsCompat.Type.statusBars()
)

fun changeStatusBarModel(insetsController: WindowInsetsControllerCompat, light: Boolean) {
    insetsController.isAppearanceLightStatusBars = light
}

fun changeStatusBarColor(
    window: Window,
    insetsController: WindowInsetsControllerCompat,
    color: Color
) {
    insetsController.isAppearanceLightStatusBars = color.luminance() > 0.5
    window.statusBarColor = color.toArgb()
}
