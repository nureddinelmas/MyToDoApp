package com.nureddinelmas.todoapp_highlevel.fragments.list.adapter


import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nureddinelmas.todoapp_highlevel.data.models.ToDoData
import com.nureddinelmas.todoapp_highlevel.data.viewModel.ToDoViewModel
import com.nureddinelmas.todoapp_highlevel.databinding.RowLayoutBinding
import com.nureddinelmas.todoapp_highlevel.fragments.list.ListFragmentDirections

class ListAdapter()  : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
	var mylist =  emptyList<ToDoData>()
	 var newList : ToDoData? = null
	
	inner class ViewHolder (val binding : RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
	
		
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding  = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}
	
	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		with(holder){
			// binding.textTitle.text = mylist[position].title
			// binding.textDescription.text = mylist[position].description
			binding.todo = mylist[position]
			
			binding.executePendingBindings()
			
			binding.checkboxRow.setOnCheckedChangeListener { buttonView, isChecked ->
				Toast.makeText(binding.checkboxRow.context, "${mylist[position].title} is $isChecked", Toast.LENGTH_LONG).show()
			}
			/*
			holder.binding.apply {
				
				binding.checkboxRow.setOnCheckedChangeListener { buttonView, isChecked ->
					val mTitle = mylist[adapterPosition].title
					val mDescription = mylist[adapterPosition].description
					val mSpinner = mylist[adapterPosition].priority
					
					newList = ToDoData(mylist[adapterPosition].id, mTitle, mSpinner, mDescription, isChecked)

				}
				
				
			}
			*/
			
			/*
			when(mylist[position].priority) {
				Priority.HIGH -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
				Priority.MEDIUM -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.yellow))
				Priority.LOW -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
				
			}
			 */
			
			binding.rowBackground.setOnClickListener {
		
				val action = ListFragmentDirections.actionListFragmentToUpdateFragment(mylist[position])
				holder.itemView.findNavController().navigate(action)
			}
		}
	}
	
	override fun getItemCount(): Int = mylist.size
	
	
	fun setData(toDoData: List<ToDoData>){
		val toDoDiffUtil = ToDoDiffUtil(mylist, toDoData)
		val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
		this.mylist = toDoData
		toDoDiffResult.dispatchUpdatesTo(this)
		
		// Bunu kaldiriyoruz. Cunku bunun yerine DiffUtil kullarak performansi artiriyoruz.
		// notifyDataSetChanged()
	}
	

	
	
}