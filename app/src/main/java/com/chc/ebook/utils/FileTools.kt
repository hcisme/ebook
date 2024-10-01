package com.chc.ebook.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.buffer
import okio.source
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.regex.Pattern

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

suspend fun extractChaptersFromUri(context: Context, fileUri: Uri): List<String> =
    withContext(Dispatchers.IO) {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(fileUri)
        val content = inputStream?.source()?.buffer()?.use { it.readUtf8() } ?: ""

        val chapterPattern = Pattern.compile("(\\s|\\n)(第)([\\u4e00-\\u9fa5a-zA-Z0-9]{1,7})章[^\\n]{1,35}(|\\n)\n")
        val matcher = chapterPattern.matcher(content)

        val chapters = mutableListOf<String>()
        while (matcher.find()) {
            chapters.add(matcher.group().trim())
        }

        chapters
    }
