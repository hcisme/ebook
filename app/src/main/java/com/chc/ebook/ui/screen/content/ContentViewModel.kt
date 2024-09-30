package com.chc.ebook.ui.screen.content

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.chc.ebook.utils.BackgroundColor
import com.chc.ebook.utils.backgroundColors

class ContentViewModel : ViewModel() {
    var isShowTool by mutableStateOf(false)
    var isShowSettingPanel by mutableStateOf(false)
    var fs by mutableIntStateOf(16)
    var currentBgColor by mutableStateOf(backgroundColors.first())
}
