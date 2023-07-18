package com.tasker.android.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tasker.android.common.model.room.LocalTask
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalTaskDao {

    @Query("Select * From local_task Where year = :year and month = :month and day = :day")
    fun getTaskByDate(year: Int, month: Int, day: Int): Flow<List<LocalTask>>

    @Insert
    suspend fun insert(task: LocalTask)
}