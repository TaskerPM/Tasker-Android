package com.tasker.android.common.model.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "local_task")
@Parcelize
data class LocalTask(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "local_id") val localId: Int = 0,
    @ColumnInfo(name = "category_color_back") var categoryColorBack: String = "",
    @ColumnInfo(name = "category_color_text") var categoryColorText: String = "",
    @ColumnInfo(name = "category_name") var categoryName: String = "",
    @ColumnInfo(name = "is_completed") var isCompleted: Boolean = false,
    @ColumnInfo(name = "task_id") val taskId: Long = 0,
    @ColumnInfo(name = "time_end") var timeEnd: String = "",
    @ColumnInfo(name = "time_start") var timeStart: String = "",
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "year") val year: Int = 0,
    @ColumnInfo(name = "month") val month: Int = 0,
    @ColumnInfo(name = "day") val day: Int = 0,
    @ColumnInfo(name = "note_list") val noteList: MutableList<String> = mutableListOf(),
) : Parcelable
