package com.chc.ebook.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chc.ebook.constant.Constants
import com.chc.ebook.room.entity.Setting

@Dao
interface SettingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(setting: Setting)

    @Query("SELECT * FROM setting WHERE id = :id")
    suspend fun getSetting(id: Int = Constants.SETTING_ID): Setting?
}
