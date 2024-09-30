package com.chc.ebook.ui.screen.home

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.ebook.room.getDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val db = remember { context.getDatabase() }
    val coroutineScope = rememberCoroutineScope()
    val homeVM = viewModel<HomeViewModel>()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                homeVM.addBook(context, uri) {
                    coroutineScope.launch {
                        db.bookshelf()?.insertOrUpdate(it)
                    }
                }
            }
        }
    }

    TopAppBar(
        modifier = modifier,
        title = { Text("书架") },
        actions = {
            IconButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "text/plain" // 可以根据需要设置文件类型
                    }
                    launcher.launch(intent)
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "导入")
            }
        }
    )
}