package com.nureddinelmas.todoapp_highlevel.fragments.list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.OvershootInterpolator
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import com.nureddinelmas.todoapp_highlevel.R
import com.nureddinelmas.todoapp_highlevel.data.models.ToDoData
import com.nureddinelmas.todoapp_highlevel.data.viewModel.SharedViewModel
import com.nureddinelmas.todoapp_highlevel.data.viewModel.ToDoViewModel
import com.nureddinelmas.todoapp_highlevel.databinding.FragmentListBinding
import com.nureddinelmas.todoapp_highlevel.databinding.RowLayoutBinding
import com.nureddinelmas.todoapp_highlevel.fragments.list.adapter.ListAdapter
import com.nureddinelmas.todoapp_highlevel.util.hideKeyword
import com.nureddinelmas.todoapp_highlevel.util.observeOnce



class ListFragment : Fragment(), SearchView.OnQueryTextListener {
	private var _binding : FragmentListBinding? = null
	private val binding get() = _binding!!
	private val listViewModel : ToDoViewModel by viewModels()
	private val sharedViewModel : SharedViewModel by viewModels()
	private lateinit var  recyclerBinding : RowLayoutBinding
	private var layout: RecyclerView.LayoutManager? = null

	private val adapter  : ListAdapter by lazy { ListAdapter() }
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		//Shared Preferences
		val sharedPreferences =
			this.activity?.getSharedPreferences("layout", Context.MODE_PRIVATE)
		
		
		
		val sharedPreferencesValue = sharedPreferences?.getString("layout", "grid")
		
		Log.e("!!!", "${sharedPreferencesValue.toString()} --- layout?")
		
		//Data Binding
		_binding = FragmentListBinding.inflate(layoutInflater)
		binding.lifecycleOwner = this
		binding.mSharedViewModel = sharedViewModel
	
		
		// Hide Keyword
		hideKeyword(requireActivity())
		recyclerBinding = RowLayoutBinding.inflate(layoutInflater)
		// binding.recyclerView.itemAnimator = SlideInUpAnimator(OvershootInterpolator(2f))
			// LandingAnimator(OvershootInterpolator(2f))
			// SlideInDownAnimator(OvershootInterpolator(2f))
		
		
		
		
		
		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager = when(sharedPreferencesValue) {
			"linear" -> LinearLayoutManager(requireContext())
			"grid" -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
			else -> StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
		}
		Log.e("!!!", "${binding.recyclerView.layoutManager.toString()} -- binding.recyclerView.layoutManager")
		swipeToDelete(binding.recyclerView)
		listViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
			
			sharedViewModel.checkIfDatabaseEmpty(data)
			 adapter.setData(data)
			
			// binding.recyclerView.scheduleLayoutAnimation()
		})
		
		/*
		// We removed this because of using data binding
		
		binding.floatingActionButton.setOnClickListener {
			findNavController().navigate(R.id.action_listFragment_to_addFragment)
		}
		
		 */
		
		binding.listLayout.setOnClickListener {
			findNavController().navigate(R.id.action_listFragment_to_updateFragment)
		}
		
		
		/*
		// This remove because we are using data binding
		sharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
			showEmptyDatabaseViews(it)
		})
		
		 */
		
		val menuHost : MenuHost = requireActivity()
		menuHost.addMenuProvider(object : MenuProvider{
			override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
				menuInflater.inflate(R.menu.list_fragment_menu, menu)
				val search = menu.findItem(R.id.menu_search)
				val searchView = search.actionView as? SearchView
				
				searchView?.isSubmitButtonEnabled = true
				searchView?.setOnQueryTextListener(this@ListFragment)
				
			}
			
			override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
				val edit = sharedPreferences?.edit()
				when(menuItem.itemId){
					R.id.menu_delete_all -> confirmRemoval()
					R.id.menu_priority_high -> sortByHigh()
					R.id.menu_priority_low -> sortByLow()
					R.id.menu_grid_list -> { binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
						edit?.putString("layout", "grid")
						edit?.apply() }
					R.id.menu_linear_list -> { binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
						edit?.putString("layout", "linear")
						edit?.apply()  }
					R.id.menu_by_isDone_true -> sortByIsDoneTrue()
					R.id.menu_by_isDone_false -> sortByIsDoneFalse()
					
				}
				
				return true
			}
			
		}, viewLifecycleOwner, Lifecycle.State.RESUMED)
		
		
		
		return binding.root
	}
	
	private fun swipeToDelete(recyclerView: RecyclerView) {
		val swipeToDeleteCallback = object : SwipeToDelete(){
			override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
				val itemToDelete= adapter.mylist[viewHolder.adapterPosition]
				listViewModel.deleteOneData(itemToDelete)
				restoreDeletedData(viewHolder.itemView, itemToDelete, viewHolder.adapterPosition)
				
			}
			
		}
		val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
		itemTouchHelper.attachToRecyclerView(recyclerView)
	}
	
	
	private fun restoreDeletedData(view: View, deletedItem: ToDoData, position: Int){
		val snackbar = Snackbar.make(view, "Deleted '${deletedItem.title}'", Snackbar.LENGTH_LONG)
		
		snackbar.setAction("Undo"){
			listViewModel.insertData(deletedItem)
			adapter.notifyDataSetChanged()
			adapter.notifyItemChanged(position)
			
		}
		snackbar.show()
	}
	
	
	/*
			// This remove because we are using data binding
	
	private fun showEmptyDatabaseViews(emptyDatabase: Boolean?) {
		if(emptyDatabase == true){
			binding.noDataTextView.visibility = View.VISIBLE
			binding.noDataImageView.visibility = View.VISIBLE
		} else {
			binding.noDataTextView.visibility = View.INVISIBLE
			binding.noDataImageView.visibility = View.INVISIBLE
		}
	}
	
	 */
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		
		

		
	}
	
	private fun sortByIsDoneFalse() {
		listViewModel.sortByIsDoneFalse.observe(viewLifecycleOwner, Observer {
			adapter.setData(it)
		})
	}
	
	private fun sortByIsDoneTrue() {
		listViewModel.sortByIsDoneTrue.observe(viewLifecycleOwner, Observer {
			adapter.setData(it)
		})
	}
	
	private fun sortByHigh() {
		listViewModel.sortByHighPriority.observe(viewLifecycleOwner, Observer {
			adapter.setData(it)
		})
	}
	
	private fun sortByLow() {
		listViewModel.sortByLowPriority.observe(viewLifecycleOwner, Observer {
			adapter.setData(it)
		})
	}
	
	
	// Show AlertDialog to Confirm Removal of All Items from Database Table
	private fun confirmRemoval() {
		val builder = AlertDialog.Builder(requireContext())
		
		builder.setPositiveButton("Yes"){_,_ ->
			listViewModel.deleteAllData()
			Toast.makeText(requireContext(), "Successfully Removed Everything!", Toast.LENGTH_LONG).show()
			
			
		}
		
		builder.setNegativeButton("No"){_,_ -> }
		builder.setTitle("Delete everything?")
		builder.setMessage("Are you sure you want to remove everything?")
		builder.create().show()
	}
	
	override fun onQueryTextSubmit(query: String?): Boolean {
		
			if (query != null) {
				searchThroughData(query)
			}
		
		return true
	}
	
	private fun searchThroughData(query: String) {
		val searhQuery: String = "%$query%"
	
		
		listViewModel.searchData(searhQuery).observeOnce(viewLifecycleOwner, Observer { list ->
			list?.let {
				adapter.setData(it)
				
			}
		})
	}
	
	override fun onQueryTextChange(newText: String?): Boolean {
		if (newText != null) {
			searchThroughData(newText)
		}
		
		return true
	}
	
	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}