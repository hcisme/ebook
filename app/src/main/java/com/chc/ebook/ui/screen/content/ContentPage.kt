package com.chc.ebook.ui.screen.content

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.ebook.room.getDatabase
import com.chc.ebook.utils.FileReader
import com.chc.ebook.utils.LocalInsetsController
import com.chc.ebook.utils.LocalNavController
import com.chc.ebook.utils.LocalWindow
import com.chc.ebook.utils.backgroundColors
import com.chc.ebook.utils.getByteCount
import com.chc.ebook.utils.hideStatusBar

@Composable
fun ContentPage(modifier: Modifier = Modifier, id: Int) {
    val navController = LocalNavController.current
    val insetsController = LocalInsetsController.current
    val context = LocalContext.current
    val window = LocalWindow.current
    val backgroundColor = MaterialTheme.colorScheme.background
    val contentVM = viewModel<ContentViewModel>()
    val db = remember { context.getDatabase() }

    LaunchedEffect(key1 = Unit) {
        hideStatusBar(insetsController)
        val dbSetting = db.setting()?.getSetting()
        if (dbSetting != null) {
            contentVM.setting = dbSetting
        }
        val book = db.bookshelf()?.getBookById(id)
        contentVM.book = book
        if (book != null) {
            val reader = FileReader(context, book.path.toUri())
            contentVM.fileReader = reader
            contentVM.chapters.addAll(reader.chapters)
            contentVM.getCurrentText(1)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColors.find { it.id == contentVM.setting.colorId }!!.color)
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

        BackHandler {
            contentVM.changeStatusBarVisible(
                visible = true,
                window = window,
                insetsController = insetsController,
                color = backgroundColor
            )
            navController.popBackStack()
        }

        ChaptersDrawer()
    }

    SettingPanel()
}
