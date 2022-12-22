package com.nureddinelmas.todoapp_highlevel.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nureddinelmas.todoapp_highlevel.R
import com.nureddinelmas.todoapp_highlevel.data.models.Priority
import com.nureddinelmas.todoapp_highlevel.data.models.ToDoData
import com.nureddinelmas.todoapp_highlevel.fragments.list.ListFragmentDirections

class BindingAdapters {
	
	companion object {
		
		@BindingAdapter("android:navigateToAddFragment")
		@JvmStatic
		fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean){
			view.setOnClickListener{
				if (navigate){
					view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
				}
			}
		}
		
		
		
		@BindingAdapter("android:emptyDatabase")
		@JvmStatic
		fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
			when(emptyDatabase.value){
				true -> view.visibility = View.VISIBLE
				false-> view.visibility = View.INVISIBLE
				else -> {view.visibility = View.INVISIBLE}
			}
		}
		
		
		@BindingAdapter("android:parsePriorityToInt")
		@JvmStatic
		fun parsePriorityToInt(view: Spinner, priority: Priority){
		when(priority){
			Priority.HIGH -> {view.setSelection(0, true)}
			Priority.MEDIUM -> {view.setSelection(1, true)}
			Priority.LOW -> {view.setSelection(2, true)}
		}
		}
		
		
		
		
		
		@BindingAdapter("android:parsePriorityToColor")
		@JvmStatic
		fun parsePriorityToColor(view: CardView, priority: Priority){
			when(priority){
				Priority.HIGH -> {view.setCardBackgroundColor(view.context.getColor(R.color.red))}
				Priority.MEDIUM -> {view.setCardBackgroundColor(view.context.getColor(R.color.yellow))}
				Priority.LOW -> {view.setCardBackgroundColor(view.context.getColor(R.color.green))}
			}
		}
		
		
		
		@BindingAdapter("android:sendDataToUpdateFragment")
		@JvmStatic
		fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem : ToDoData){
			view.setOnClickListener {
				val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
				view.findNavController().navigate(action)
			}
		}
		
	}
}