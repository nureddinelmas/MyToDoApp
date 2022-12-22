package com.nureddinelmas.todoapp_highlevel.data.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nureddinelmas.todoapp_highlevel.data.ToDoDatabase
import com.nureddinelmas.todoapp_highlevel.data.models.ToDoData
import com.nureddinelmas.todoapp_highlevel.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application): AndroidViewModel(application) {
	
	private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
	private val repository: ToDoRepository = ToDoRepository(toDoDao)
	
	 val getAllData : LiveData<List<ToDoData>> = repository.getAllData
	
	
	fun insertData(toDoData: ToDoData){
		viewModelScope.launch (Dispatchers.IO) {
			repository.insertData(toDoData)
		}
	}
	
	
fun updateData(toDoData: ToDoData){
	viewModelScope.launch (Dispatchers.IO) {
		repository.updateData(toDoData)
	}
}
	
	
	fun deleteOneData(toDoData: ToDoData){
		viewModelScope.launch (Dispatchers.IO) {
			repository.deleteOneData(toDoData)
		}
	}
	
	
	fun deleteAllData(){
		viewModelScope.launch (Dispatchers.IO){
			repository.deleteAllData()
		}
	}
	
	
	fun searchData(searchText: String) : LiveData<List<ToDoData>>{
		
			return repository.searchData(searchText)
		
	}
	
	val sortByHighPriority : LiveData<List<ToDoData>> = repository.sortByHighPriority
	
	val sortByLowPriority : LiveData<List<ToDoData>> = repository.sortByLowPriority
	
	val sortByIsDoneTrue : LiveData<List<ToDoData>> = repository.sortByIsDoneTrue
	val sortByIsDoneFalse : LiveData<List<ToDoData>> = repository.sortByIsDoneFalse
	
}