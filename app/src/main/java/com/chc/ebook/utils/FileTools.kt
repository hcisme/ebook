package com.chc.ebook.utils

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

fun getFileNameByUri(context: Context, uri: Uri): String? {
    val documentFile = DocumentFile.fromSingleUri(context, uri)
    return documentFile?.name
}

suspend fun readTextFromUri(context: Context, uri: Uri): String = withContext(Dispatchers.IO) {
    val stringBuilder = StringBuilder()
    var count = 0

    try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line = reader.readLine()
                while (line != null && count < 100) {
                    stringBuilder.append(line).append("\n")
                    line = reader.readLine()
                    count += 1
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    stringBuilder.toString()
}

