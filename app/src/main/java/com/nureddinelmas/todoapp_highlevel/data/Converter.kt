package com.nureddinelmas.todoapp_highlevel.data

import androidx.room.TypeConverter
import com.nureddinelmas.todoapp_highlevel.data.models.Priority

class Converter {
	
	@TypeConverter
	fun fromPriority(priority: Priority): String = priority.name
	
	
	@TypeConverter
	fun toPriority(priority: String) : Priority {
		return Priority.valueOf(priority)
	}
}