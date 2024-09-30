package com.chc.ebook.ui.screen.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.ebook.utils.LocalInsetsController
import com.chc.ebook.utils.LocalWindow
import com.chc.ebook.utils.changeStatusBarColor
import com.chc.ebook.utils.hideStatusBar
import com.chc.ebook.utils.showStatusBar

@Composable
fun Body(modifier: Modifier = Modifier) {
    val window = LocalWindow.current
    val insetsController = LocalInsetsController.current
    val onBackgroundColor = MaterialTheme.colorScheme.onBackground
    val backgroundColor = MaterialTheme.colorScheme.background
    val contentVM = viewModel<ContentViewModel>()

    Box(
        modifier = modifier
            .padding(top = 24.dp)
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                contentVM.isShowTool = !contentVM.isShowTool
                if (contentVM.isShowTool) {
                    changeStatusBarColor(window, insetsController, onBackgroundColor)
                    showStatusBar(insetsController)
                    return@clickable
                }
                hideStatusBar(insetsController)
                changeStatusBarColor(window, insetsController, backgroundColor)
            }
    ) {
        Column {
            for (item in 1..100) {
                Text("$item")
            }
        }
    }
}