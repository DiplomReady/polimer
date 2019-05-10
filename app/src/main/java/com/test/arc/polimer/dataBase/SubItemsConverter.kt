package com.test.arc.polimer.dataBase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.arc.polimer.model.SubItem
import java.util.*

class SubItemConverter {

    @TypeConverter
    fun fromSubItemList(value: ArrayList<SubItem>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<SubItem>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSubItemList(value: String): ArrayList<SubItem> {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<SubItem>>() {}.type
        return gson.fromJson(value, type)
    }
}