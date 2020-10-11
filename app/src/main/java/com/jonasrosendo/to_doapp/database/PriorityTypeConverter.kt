package com.jonasrosendo.to_doapp.database

import androidx.room.TypeConverter
import com.jonasrosendo.to_doapp.data.model.Priority

class PriorityTypeConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun fromString(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}