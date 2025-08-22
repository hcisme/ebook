package com.chc.ebook.utils

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.Buffer
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

/**
 * 分片读文件
 */
data class FileReader(private val context: Context, private val uri: Uri) {
    /**
     * 目录
     */
    val chapters = mutableListOf<String>()

    init {
        extractChapters()
    }

    private fun extractChapters() {
        val content =
            context.contentResolver.openInputStream(uri)?.source()?.buffer()?.use { it.readUtf8() }
                ?: ""

        val chapterPattern =
            Pattern.compile("(\\s|\\n)(第)([\\u4e00-\\u9fa5a-zA-Z0-9]{1,7})章[^\\n]{1,35}(|\\n)\n")
        val matcher = chapterPattern.matcher(content)

        while (matcher.find()) {
            chapters.add(matcher.group().trim())
        }
    }

    fun readPart(startOffset: Long, length: Long): String {
        context.contentResolver.openInputStream(uri)?.use { stream ->
            val source = stream.source().buffer()
            source.skip(startOffset)
            val buffer = Buffer()
            source.read(buffer, length)
            val text = buffer.readUtf8()

            return text
        }
        return ""
    }
}

fun getByteCount(input: String): Long {
    val byteArray = input.toByteArray(Charsets.UTF_8)
    return byteArray.size.toLong()
}
