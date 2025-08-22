package com.chc.ebook.ui.screen.content

import android.view.Window
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModel
import com.chc.ebook.constant.Constants
import com.chc.ebook.room.entity.Bookshelf
import com.chc.ebook.room.entity.Setting
import com.chc.ebook.utils.FileReader
import com.chc.ebook.utils.changeStatusBarColorAndVisible
import com.chc.ebook.utils.getByteCount

class ContentViewModel : ViewModel() {
    var batteryLevel by mutableIntStateOf(0)
    var isShowTool by mutableStateOf(false)
    var isShowSettingPanel by mutableStateOf(false)
    var isShowChaptersDrawer by mutableStateOf(false)
    var boxWidthPx by mutableIntStateOf(0)

    var fileReader: FileReader? = null
    var page by mutableIntStateOf(1)
    var cursor by mutableFloatStateOf(0f)
    var pageCharCount by mutableIntStateOf(0)

    var book by mutableStateOf<Bookshelf?>(null)
    var text by mutableStateOf("")
    var chapters = mutableStateListOf<String>()
    var setting by mutableStateOf(Setting(id = Constants.SETTING_ID, colorId = 1, fs = 16))

    fun getCurrentText(currentPage: Int, len: Long = 8000) {
//        Log.i("@@", len.toString())
        page = currentPage
        cursor += getByteCount(text)
        text = fileReader?.readPart(cursor.toLong(), len) ?: ""
    }

    fun changeStatusBarVisible(
        visible: Boolean,
        window: Window,
        insetsController: WindowInsetsControllerCompat,
        color: Color
    ) {
        isShowTool = visible
        changeStatusBarColorAndVisible(
            visible = visible,
            window,
            insetsController,
            color
        )
    }
}
