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
}