package com.tasker.android.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson

class RoomConverter {

    @TypeConverter
    fun listToJson(list: List<String>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun jsonToList(json: String): List<String> {
        return Gson().fromJson(json, Array<String>::class.java).toMutableList()
    }
}