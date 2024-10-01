package com.chc.ebook.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chc.ebook.components.Book
import com.chc.ebook.navigationHost.CONTENTPAGE
import com.chc.ebook.room.getDatabase
import com.chc.ebook.utils.LocalNavController

@Composable
fun HomePage() {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val db = remember { context.getDatabase() }
    val homeVM = viewModel<HomeViewModel>()

    LaunchedEffect(key1 = Unit) {
        homeVM.bookList.clear()
        val bookList = db.bookshelf()?.getAllBook()
        homeVM.bookList.addAll(bookList ?: listOf())
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar()
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(homeVM.bookList) { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Book(
                        text = item.name,
                        onClick = {
                            navController.navigate("$CONTENTPAGE/${item.id}")
                        }
                    )
                }
            }
        }
    }
}