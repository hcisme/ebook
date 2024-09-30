package com.chc.ebook.utils

import android.view.Window
import androidx.compose.runtime.compositionLocalOf
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController

/**
 * 路由 NavHostController
 */
val LocalNavController = compositionLocalOf<NavHostController> { error("No NavController found!") }

val LocalInsetsController =
    compositionLocalOf<WindowInsetsControllerCompat> { error("No insetsController found!") }

val LocalWindow = compositionLocalOf<Window> { error("No Window found!") }
