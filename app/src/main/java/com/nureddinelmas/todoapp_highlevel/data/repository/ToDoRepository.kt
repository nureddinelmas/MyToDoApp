package com.nureddinelmas.todoapp_highlevel.data.repository

import androidx.lifecycle.LiveData
import com.nureddinelmas.todoapp_highlevel.data.ToDoDao
import com.nureddinelmas.todoapp_highlevel.data.models.ToDoData

class ToDoRepository (private val toDoDao: ToDoDao){
	
	val getAllData : LiveData<List<ToDoData>> = toDoDao.getAllData()
	val sortByHighPriority : LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()
	val sortByLowPriority : LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()
	val sortByIsDoneTrue : LiveData<List<ToDoData>> = toDoDao.updateIsDone(true)
	val sortByIsDoneFalse : LiveData<List<ToDoData>> = toDoDao.updateIsDone(false)
	suspend fun insertData(toDoData: ToDoData){
		toDoDao.insertData(toDoData)
	}
	
	
	suspend fun updateData(toDoData: ToDoData){
		toDoDao.updateData(toDoData)
	}
	
	
	suspend fun deleteOneData(toDoData: ToDoData){
		toDoDao.deleteOneData(toDoData)
	}
	
	
	suspend fun deleteAllData(){
		toDoDao.deleteAllData()
	}
	
	fun searchData(searchText: String): LiveData<List<ToDoData>>{
		return toDoDao.searchData(searchText)
	}
	
	
}