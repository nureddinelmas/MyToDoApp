package com.nureddinelmas.todoapp_highlevel.fragments.add

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.nureddinelmas.todoapp_highlevel.R
import com.nureddinelmas.todoapp_highlevel.data.models.ToDoData
import com.nureddinelmas.todoapp_highlevel.data.viewModel.SharedViewModel
import com.nureddinelmas.todoapp_highlevel.data.viewModel.ToDoViewModel
import com.nureddinelmas.todoapp_highlevel.databinding.FragmentAddBinding

class AddFragment : Fragment() {
	
	private val addViewModel : ToDoViewModel by viewModels()
	private val sharedViewModel : SharedViewModel by viewModels()
	private lateinit var binding : FragmentAddBinding
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
	
binding = FragmentAddBinding.inflate(inflater)
		binding.spinnerEt.onItemSelectedListener = sharedViewModel.listener
		return binding.root
	}
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val menuHost : MenuHost = requireActivity()
		menuHost.addMenuProvider(object : MenuProvider{
			override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
				menuInflater.inflate(R.menu.add_fragment_menu, menu)
			}
			
			override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
				if (menuItem.itemId == android.R.id.home){
					findNavController().navigate(R.id.action_addFragment_to_listFragment)
				} else if (menuItem.itemId == R.id.add_menu) {
					insertDataToDb()
				}
				return true
			}
			
		}, viewLifecycleOwner, Lifecycle.State.RESUMED)
	}
	
	private fun insertDataToDb() {
		val mTitle = binding.titleEt.text.toString()
		val mDesc = binding.descriptionEt.text.toString()
		val mSpinnerPosition = binding.spinnerEt.selectedItemPosition
		val validation = sharedViewModel.verifyDataFromUser(mTitle, mDesc)
		
		
		if (validation){
		
			val mToDoData =  ToDoData(0, mTitle, sharedViewModel.parsePriority(mSpinnerPosition), mDesc,false)
			addViewModel.insertData(mToDoData)
			Toast.makeText(requireContext(), "Succesfully saved", Toast.LENGTH_LONG).show()
			
			// Navigate to Back
			findNavController().navigate(R.id.action_addFragment_to_listFragment)
		} else {
			Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_LONG).show()
		}
	}
	
	

}