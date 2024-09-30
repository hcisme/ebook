package com.chc.ebook.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.chc.ebook.constant.Constants
import com.chc.ebook.room.dao.BookshelfDao
import com.chc.ebook.room.dao.SettingDao
import com.chc.ebook.room.entity.Bookshelf
import com.chc.ebook.room.entity.Setting
import kotlin.concurrent.Volatile

@Database(
    entities = [Bookshelf::class, Setting::class],
    version = Constants.NEW_ROOM_DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookshelf(): BookshelfDao?
    abstract fun setting(): SettingDao?

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_database"
                        ).addMigrations(MIGRATION_SETTING).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}

fun Context.getDatabase() = AppDatabase.getDatabase(this)
