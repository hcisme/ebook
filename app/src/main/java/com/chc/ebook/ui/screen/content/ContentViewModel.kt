package com.chc.ebook.ui.screen.content

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ContentViewModel : ViewModel() {
    var isShowTool by mutableStateOf(false)
}