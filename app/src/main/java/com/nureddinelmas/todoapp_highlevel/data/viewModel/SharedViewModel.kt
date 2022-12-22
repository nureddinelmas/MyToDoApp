package com.nureddinelmas.todoapp_highlevel.data.viewModel

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.arch.core.util.Function
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nureddinelmas.todoapp_highlevel.R
import com.nureddinelmas.todoapp_highlevel.data.models.Priority
import com.nureddinelmas.todoapp_highlevel.data.models.ToDoData
import com.nureddinelmas.todoapp_highlevel.databinding.CustomDialogBinding

class SharedViewModel(application: Application) : AndroidViewModel(application) {
 
	/* ------------------------- List Fragments ---------------------------------------- */
	
	val emptyDatabase : MutableLiveData<Boolean>  = MutableLiveData(true)
	
	fun checkIfDatabaseEmpty(toDoData: List<ToDoData>){
		emptyDatabase.value = toDoData.isEmpty()
	}
	

	/* ------------------------- Add and Update Fragments ---------------------------------------- */
	
	val listener : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
		override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
			when(position){
				0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red)) }
				1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
				2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}
				
			}
		}
		
		override fun onNothingSelected(parent: AdapterView<*>?) {}
		
	}
	fun verifyDataFromUser(title: String, description : String) : Boolean{
		return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(description))
	}
	
	
	 fun parsePriority(position: Int) : Priority {
		return when(position){
			
			0 -> {
				Priority.HIGH}
			1 -> {
				Priority.MEDIUM}
			2 -> {
				Priority.LOW}
			
			else -> {Priority.LOW}
		}
	}
	
	fun parsePriorityToInt(priority: Priority) : Int{
		return when (priority){
			Priority.HIGH -> 0
			Priority.MEDIUM -> 1
			Priority.LOW ->2
		}
	}
	
	
	
}