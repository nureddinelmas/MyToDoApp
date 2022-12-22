package com.nureddinelmas.todoapp_highlevel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		// Navigation Controller
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
		val navController = navHostFragment.navController
		setupActionBarWithNavController(navController)
		
		/*
		// Menu Options
		addMenuProvider(object : MenuProvider {
			override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
				menuInflater.inflate(R.menu.list_fragment_menu, menu)
				
			}
			
			override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
				return true
			}
			
		})
		
		 */
	}
	
	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.navHostFragment)
		return navController.navigateUp() || super.onSupportNavigateUp()
	}
}