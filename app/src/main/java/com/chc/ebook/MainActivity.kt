package com.chc.ebook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.documentfile.provider.DocumentFile
import androidx.navigation.compose.rememberNavController
import com.chc.ebook.navigationHost.NavHostComp
import com.chc.ebook.room.getDatabase
import com.chc.ebook.ui.theme.EbookTheme
import com.chc.ebook.utils.LocalInsetsController
import com.chc.ebook.utils.LocalNavController
import com.chc.ebook.utils.LocalWindow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val view = LocalView.current
            val rememberNavController = rememberNavController()
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

            LaunchedEffect(key1 = Unit) {
                context.getDatabase()
            }

            CompositionLocalProvider(
                LocalNavController provides rememberNavController,
                LocalWindow provides window,
                LocalInsetsController provides insetsController
            ) {
                EbookTheme {
                    NavHostComp()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )

        FilePicker()
    }
}

@Composable
fun FilePicker() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var fileName by remember { mutableStateOf<String?>(null) }
    var fileContent by remember { mutableStateOf<String?>(null) }
    var elapsedTime by remember { mutableLongStateOf(0L) }
    var isReading by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedFileUri = uri
                fileName = getFileName(context, uri)
                isReading = true
                coroutineScope.launch {
                    val startTime = System.currentTimeMillis()
                    fileContent = readTextFileFromUri(context, uri)
                    val endTime = System.currentTimeMillis()
                    elapsedTime = (endTime - startTime) / 1000
                    isReading = false
                }
            }
        }
    }

    LaunchedEffect(isReading) {
        while (isReading) {
            delay(1000)
            elapsedTime += 1
        }
    }

    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/plain" // 可以根据需要设置文件类型
            }
            launcher.launch(intent)
        }
    ) {
        Text(text = "选择文件")
    }

    Text(text = "选中的文件: $fileName")
    Text(text = "读取耗时: $elapsedTime s")
    LazyColumn {
        item {
            Text(text = "文件内容: $fileContent")
        }
    }
}

fun getFileName(context: Context, uri: Uri): String? {
    val documentFile = DocumentFile.fromSingleUri(context, uri)
    return documentFile?.name
}

suspend fun readTextFileFromUri(context: Context, uri: Uri): String {
    return withContext(Dispatchers.IO) {
//        val documentFile = DocumentFile.fromSingleUri(context, uri)
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val content = StringBuilder()
        var charCount = 0
        reader.use {
            var line = it.readLine()
            while (line != null && charCount < 100000) {
                content.append(line).append("\n")
                it.readLine().let { l ->
                    charCount += l.length
                    line = l
                }
            }
        }
        content.toString()
    }
}
