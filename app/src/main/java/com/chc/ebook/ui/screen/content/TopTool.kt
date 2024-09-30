package com.chc.ebook.ui.screen.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.ebook.utils.LocalInsetsController
import com.chc.ebook.utils.LocalNavController
import com.chc.ebook.utils.LocalWindow
import com.chc.ebook.utils.changeStatusBarColor
import com.chc.ebook.utils.showStatusBar

@Composable
fun TopTool(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val insetsController = LocalInsetsController.current
    val window = LocalWindow.current
    val backgroundColor = MaterialTheme.colorScheme.background
    val contentVM = viewModel<ContentViewModel>()

    AnimatedVisibility(
        modifier = modifier,
        visible = contentVM.isShowTool,
        enter = slideInVertically(
            initialOffsetY = { -it }
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { -it }
        ) + fadeOut()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.onBackground),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    changeStatusBarColor(window, insetsController, backgroundColor)
                    showStatusBar(insetsController)
                    navController.popBackStack()
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}
