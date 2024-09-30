package com.chc.ebook.ui.screen.content

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.chc.ebook.constant.Constants
import com.chc.ebook.room.entity.Bookshelf
import com.chc.ebook.room.entity.Setting

class ContentViewModel : ViewModel() {
    var isShowTool by mutableStateOf(false)
    var isShowSettingPanel by mutableStateOf(false)
    var book by mutableStateOf<Bookshelf?>(null)
    var text by mutableStateOf("")
    var setting by mutableStateOf(Setting(id = Constants.SETTING_ID, colorId = 1, fs = 16))
}
