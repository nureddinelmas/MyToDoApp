package com.nureddinelmas.todoapp_highlevel.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nureddinelmas.todoapp_highlevel.data.models.Priority
import com.nureddinelmas.todoapp_highlevel.data.models.ToDoData


@Dao
interface ToDoDao {
	@Query("SELECT * FROM todo_table ORDER BY id ASC")
	fun getAllData() : LiveData<List<ToDoData>>
	
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertData(toDoData: ToDoData)
	
	@Update
	suspend fun updateData(toDoData: ToDoData)
	
	@Delete
	suspend fun deleteOneData(toDoData: ToDoData)
	
	
	@Query("DELETE FROM todo_table")
	suspend fun deleteAllData()
	
	
	@Query("SELECT * FROM todo_table WHERE title LIKE :searchText")
	fun searchData(searchText: String) : LiveData<List<ToDoData>>
	
	@Query("SELECT * FROM todo_table WHERE isDone LIKE :isDone")
	fun updateIsDone(isDone : Boolean) : LiveData<List<ToDoData>>
	
	@Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
	fun sortByHighPriority(): LiveData<List<ToDoData>>
	
	@Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
	fun sortByLowPriority(): LiveData<List<ToDoData>>
}