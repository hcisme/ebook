package com.chc.ebook.ui.screen.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.ebook.utils.backgroundColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingPanel(modifier: Modifier = Modifier) {
    val contentVM = viewModel<ContentViewModel>()

    if (contentVM.isShowSettingPanel) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = {
                contentVM.isShowSettingPanel = false
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { contentVM.fs -= 1 },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = null)
                    }
                    Text("${contentVM.fs}")
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) { contentVM.fs += 1 },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                LazyRow {
                    items(backgroundColors) { item ->
                        Spacer(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 8.dp)
                                .width(60.dp)
                                .height(40.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(item.color)
                                .border(
                                    0.4.dp,
                                    if (contentVM.currentBgColor.id == item.id) Color.Red else Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    contentVM.currentBgColor = item
                                }
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
            }
        }
    }
}
