package com.chc.ebook.ui.screen.home

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.chc.ebook.room.entity.Bookshelf
import com.chc.ebook.utils.getFileNameByUri

class HomeViewModel : ViewModel() {
    val bookList = mutableStateListOf<Bookshelf>()

    fun addBook(context: Context, uri: Uri, onSuccess: (book: Bookshelf) -> Unit = {}) {
        val fileName = getFileNameByUri(context, uri)!!
        if (bookList.any { it.name == fileName }) {
            Toast.makeText(context, "已存在", Toast.LENGTH_SHORT).show()
            return
        }
        val book = Bookshelf(
            name = fileName,
            path = uri.toString()
        )
        bookList.add(book)
        onSuccess(book)
    }
}