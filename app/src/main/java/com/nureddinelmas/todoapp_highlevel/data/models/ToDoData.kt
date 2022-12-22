package com.nureddinelmas.todoapp_highlevel.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "todo_table")
@Parcelize
data class ToDoData(
	@PrimaryKey(autoGenerate = true)
	var id: Int,
	var title: String?,
	var priority: Priority,
	var description: String?,
	var isDone: Boolean?
) : Parcelable