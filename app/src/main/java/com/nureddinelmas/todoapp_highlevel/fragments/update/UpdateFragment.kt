package com.nureddinelmas.todoapp_highlevel.fragments.update

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nureddinelmas.todoapp_highlevel.R
import com.nureddinelmas.todoapp_highlevel.data.models.Priority
import com.nureddinelmas.todoapp_highlevel.data.models.ToDoData
import com.nureddinelmas.todoapp_highlevel.data.viewModel.SharedViewModel
import com.nureddinelmas.todoapp_highlevel.data.viewModel.ToDoViewModel
import com.nureddinelmas.todoapp_highlevel.databinding.CustomDialogBinding
import com.nureddinelmas.todoapp_highlevel.databinding.FragmentUpdateBinding


class UpdateFragment : Fragment(), MenuProvider {
	private lateinit var binding : FragmentUpdateBinding
	// private val args : UpdateFragmentArgs by navArgs()
	private val sharedViewModel : SharedViewModel by viewModels()
	private val todoViewModel: ToDoViewModel by viewModels()
	private val args by navArgs<UpdateFragmentArgs>()
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentUpdateBinding.inflate(layoutInflater)
		binding.args = args.currentItem
		binding.spinnerUpdate.onItemSelectedListener = sharedViewModel.listener
	activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
		
		
		
		/*
		binding.titleUpdate.setText(args.currentItem.title)
		binding.descriptionUpdate.setText(args.currentItem.description)
		binding.spinnerUpdate.setSelection(sharedViewModel.parsePriorityToInt(args.currentItem.priority))
		
		 */
		return binding.root
	}
	
	
	override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
		menuInflater.inflate(R.menu.update_fragment_menu, menu)
	}
	
	override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
		// Burada ayri bir yontemde kullanilabilir ->
		// android.R.id.home -> requireActivity().onBackPressed()
		
		 if (menuItem.itemId == android.R.id.home){
			// findNavController().navigate(R.id.action_updateFragment_to_listFragment)
			 requireActivity().onBackPressed()
		} else if(menuItem.itemId == R.id.menu_save){
			
			 updateData()
			
			findNavController().navigate(R.id.action_updateFragment_to_listFragment)
		} else if (menuItem.itemId == R.id.menu_delete){
			customDialogFunction()
			
		}
		return true
	}
	
	private fun updateData(){
		val mTitle = binding.titleUpdate.text.toString()
		val mDesc = binding.descriptionUpdate.text.toString()
		val mSpinnerPosition = binding.spinnerUpdate.selectedItemPosition
		val validation = sharedViewModel.verifyDataFromUser(mTitle, mDesc)
		val isDone = binding.checkboxUpdate.isChecked
		if (validation){
			val todo = ToDoData(args.currentItem.id, mTitle, sharedViewModel.parsePriority(mSpinnerPosition), mDesc, isDone)
			todoViewModel.updateData(todo)
			Toast.makeText(requireContext(), "Succesfully updated", Toast.LENGTH_LONG).show()
		} else {
			Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_LONG).show()
		}
	}
	
	
	private fun customDialogFunction(){
		val binding : CustomDialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(requireContext()))
		val customDialog = Dialog(requireActivity())
		
		customDialog.setContentView(binding.root)
		
		binding.titleText.text = "Delete '${args.currentItem.title}'"
		binding.descriptionText.text = "Are you sure you want to remove '${args.currentItem.title}'?"
		binding.tvSubmit.text = "DELETE"
		
		binding.tvSubmit.setOnClickListener {
			todoViewModel.deleteOneData(args.currentItem)
			findNavController().navigate(R.id.action_updateFragment_to_listFragment)
			customDialog.dismiss()
		}
		binding.tvCancel.setOnClickListener {
			Toast.makeText(context, "clicked cancel", Toast.LENGTH_LONG).show()
			customDialog.dismiss()
		}
		
		customDialog.show()
	}
}