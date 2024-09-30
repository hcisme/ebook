package com.chc.ebook.utils

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color

/**
 * Hex 转 Color
 */
fun hex2Color(colorString: String) = Color(parseColor(colorString))