package com.chc.ebook.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chc.ebook.constant.Constants

/**
 * 第一次迁移 增加 setting 表
 */
val MIGRATION_SETTING = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `setting` (
                `id` INTEGER NOT NULL,
                `color_id` INTEGER NOT NULL,
                `fs` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
            )
        """.trimIndent()
        )
    }
}
