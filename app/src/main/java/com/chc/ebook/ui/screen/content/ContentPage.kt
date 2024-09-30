package com.chc.ebook.ui.screen.content

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.ebook.utils.LocalInsetsController
import com.chc.ebook.utils.LocalNavController
import com.chc.ebook.utils.LocalWindow
import com.chc.ebook.utils.changeStatusBarColor
import com.chc.ebook.utils.hideStatusBar
import com.chc.ebook.utils.showStatusBar

@Composable
fun ContentPage(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val insetsController = LocalInsetsController.current
    val window = LocalWindow.current
    val backgroundColor = MaterialTheme.colorScheme.background
    val contentVM = viewModel<ContentViewModel>()

    LaunchedEffect(key1 = Unit) {
        hideStatusBar(insetsController)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(contentVM.currentBgColor.color)
    ) {

        Body(
            modifier = Modifier
                .navigationBarsPadding()
        )

        TopTool(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
        )
        BottomTool(
            modifier = Modifier
                .navigationBarsPadding()
                .align(Alignment.BottomCenter)
        )
    }

    SettingPanel()

    BackHandler {
        changeStatusBarColor(window, insetsController, backgroundColor)
        showStatusBar(insetsController)
        navController.popBackStack()
    }
}
