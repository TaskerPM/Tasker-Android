package com.tasker.android.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tasker.android.common.model.room.LocalTask

@Database(
    entities = [LocalTask::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localTaskDao(): LocalTaskDao
}