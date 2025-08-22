package com.chc.ebook.ui.screen.content

import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Battery0Bar
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.ebook.utils.BatteryReceiver
import com.chc.ebook.utils.LocalInsetsController
import com.chc.ebook.utils.LocalWindow
import com.chc.ebook.utils.handleTouchEvents
import kotlinx.coroutines.coroutineScope

@Composable
fun Body(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val window = LocalWindow.current
    val insetsController = LocalInsetsController.current
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground
    val contentVM = viewModel<ContentViewModel>()

    DisposableEffect(Unit) {
        val receiver = BatteryReceiver { level ->
            contentVM.batteryLevel = level
        }
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        context.registerReceiver(receiver, intentFilter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

//    LaunchedEffect(contentVM.length) {
//        if (contentVM.length != 0) {
//            contentVM.getCurrentText(contentVM.page)
//        }
//    }

    Box(
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxSize()
            .pointerInput(Unit) {
                coroutineScope {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            if (event.changes.any { it.pressed }) {
                                val position = event.changes.first().position
                                handleTouchEvents(
                                    position.x,
                                    contentVM.boxWidthPx.toFloat(),
                                    onClickLeft = {
                                        if (contentVM.page == 1) {
                                            return@handleTouchEvents
                                        }
                                        contentVM.getCurrentText(contentVM.page - 1)
                                    },
                                    onClickBetween = {
                                        contentVM.changeStatusBarVisible(
                                            visible = !contentVM.isShowTool,
                                            window = window,
                                            insetsController = insetsController,
                                            color = onBackgroundColor
                                        )
                                    },
                                    onClickRight = {
                                        contentVM.getCurrentText(contentVM.page + 1)
                                    }
                                )
                            }
                        }
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${contentVM.pageCharCount}")
                Text(contentVM.book?.name ?: "", fontSize = 12.sp)
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.dp, Color.Black)
                .onGloballyPositioned { coordinates ->
                    contentVM.boxWidthPx = coordinates.size.width
//                    contentVM.length = calculateCharacterCapacity(
//                        density,
//                        coordinates.size.width.toFloat(),
//                        coordinates.size.height.toFloat(),
//                        contentVM.setting.fs.sp
//                    )
                }
            ) {
                if (contentVM.book != null) {
                    Text(
                        text = contentVM.text,
                        fontSize = contentVM.setting.fs.sp,
                        onTextLayout = {
                            try {
                                val lineNum =
                                    it.getLineForVerticalPosition(it.size.height.toFloat())
                                if (lineNum != 0) {
                                    val totalNum = it.getLineEnd(lineNum, false)
                                    contentVM.pageCharCount = totalNum
                                    contentVM.text = contentVM.text.substring(0, totalNum)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Battery0Bar,
                        contentDescription = null,
                        tint = LocalContentColor.current.copy(alpha = 0.6f),
                        modifier = Modifier.rotate(90f)
                    )
                    Text("${contentVM.batteryLevel}%", fontSize = 12.sp)
                }
            }
        }
    }
}