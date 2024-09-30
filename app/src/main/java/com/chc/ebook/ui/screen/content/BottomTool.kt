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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BottomTool(modifier: Modifier = Modifier) {
    val contentVM = viewModel<ContentViewModel>()

    AnimatedVisibility(
        modifier = modifier,
        visible = contentVM.isShowTool,
        enter = slideInVertically(
            initialOffsetY = { it }
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { it }
        ) + fadeOut()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onBackground
            ) {
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        contentVM.isShowSettingPanel = true
                    },
                    icon = {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.background.copy(alpha = 0.7f)
                        )
                    },
                    label = {
                        Text(
                            "阅读设置", color = MaterialTheme.colorScheme.background.copy(
                                alpha = 0.7f
                            )
                        )
                    }
                )
            }
        }
    }
}
