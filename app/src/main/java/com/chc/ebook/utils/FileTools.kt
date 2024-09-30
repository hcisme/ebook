package com.chc.ebook.utils

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile

fun getFileNameByUri(context: Context, uri: Uri): String? {
    val documentFile = DocumentFile.fromSingleUri(context, uri)
    return documentFile?.name
}
