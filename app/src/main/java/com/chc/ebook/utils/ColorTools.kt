package com.chc.ebook.utils

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color

/**
 * Hex 转 Color
 */
fun hex2Color(colorString: String) = Color(parseColor(colorString))

/**
 * 阅读小说时的背景色
 */
val backgroundColors = listOf(
    BackgroundColor(1, "米黄色", Color(0xFFFFF8DC)),
    BackgroundColor(2, "象牙色", Color(0xFFFFFFF0)),
    BackgroundColor(3, "浅灰色", Color(0xFFD3D3D3)),
    BackgroundColor(4, "杏仁色", Color(0xFFFAEBD7)),
    BackgroundColor(5, "蜂蜜色", Color(0xFFF0FFF0)),
    BackgroundColor(6, "薄荷奶油色", Color(0xFFF5FFFA)),
    BackgroundColor(7, "海贝色", Color(0xFFFFF5EE)),
    BackgroundColor(8, "薰衣草色", Color(0xFFFFF0F5)),
    BackgroundColor(9, "亚麻色", Color(0xFFFAF0E6)),
    BackgroundColor(10, "老蕾丝色", Color(0xFFFDF5E6))
)

data class BackgroundColor(val id: Int, val name: String, val color: Color)
