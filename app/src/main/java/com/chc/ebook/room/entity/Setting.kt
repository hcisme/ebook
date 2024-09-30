package com.chc.ebook.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chc.ebook.constant.Constants

@Entity(tableName = "setting")
data class Setting(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int = Constants.SETTING_ID,

    @ColumnInfo(name = "color_id")
    val colorId: Int,

    @ColumnInfo(name = "fs")
    val fs: Int,
)
