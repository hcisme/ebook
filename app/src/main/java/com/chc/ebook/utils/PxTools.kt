package com.chc.ebook.utils

import android.graphics.Paint
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit

fun spToPx(density: Density, spValue: TextUnit) = with(density) { spValue.toPx() }

fun getFontDimensions(fontSizePx: Float): Pair<Float, Float> {
    val paint = Paint().apply {
        textSize = fontSizePx
    }

    // 获取字体宽度
    val textWidth = paint.measureText("字")

    // 获取字体高度
    val fontMetrics = paint.fontMetrics
    val textHeight = fontMetrics.bottom - fontMetrics.top

    return Pair(textWidth, textHeight)
}

/**
 * 计算 区域范围内 字符容量
 */
fun calculateCharacterCapacity(
    density: Density,
    boxWidthPx: Float,
    boxHeightPx: Float,
    fontSizeSp: TextUnit
): Int {
    val fontSizePx = spToPx(density, fontSizeSp)

    val (width, height) = getFontDimensions(fontSizePx)

    val charsPerLine = (boxWidthPx / width).toInt() // 计算每行能容纳的字符数
    val lines = (boxHeightPx / height).toInt() // 计算能容纳的行数
//    Log.i(
//        "@@",
//        "${charsPerLine}-----${lines}"
//    )
    // 总字符数
    return charsPerLine * lines
}

fun handleTouchEvents(
    clickPositionX: Float,
    totalWidth: Float,
    onClickLeft: () -> Unit = {},
    onClickBetween: () -> Unit = {},
    onClickRight: () -> Unit = {}
) {
    when {
        clickPositionX < totalWidth / 3 -> {
            onClickLeft()
        }

        clickPositionX in (totalWidth / 3)..(totalWidth * 2 / 3) -> {
            onClickBetween()
        }

        clickPositionX > totalWidth * 2 / 3 -> {
            onClickRight()
        }
    }
}
