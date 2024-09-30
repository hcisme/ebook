package com.chc.ebook.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chc.ebook.room.entity.Bookshelf

@Dao
interface BookshelfDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(bookInfo: Bookshelf)

    @Delete
    suspend fun delete(bookInfo: Bookshelf): Int

    @Query("SELECT * FROM bookshelf WHERE id = :id")
    suspend fun getBookById(id: Int): Bookshelf?

    @Query("SELECT * FROM bookshelf")
    suspend fun getAllBook(): List<Bookshelf>
}
