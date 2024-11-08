package com.skymob.crosoftenteste.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.skymob.crosoftenteste.R
import com.skymob.crosoftenteste.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private var shouldShowMenu = false

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.apply {
            setupWithNavController(navController)
        }

        with(binding) {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                supportActionBar?.setDisplayHomeAsUpEnabled(destination.id != R.id.listFragment)
                setVisibilityComponents(destination.id)
            }
        }
    }

    private fun setVisibilityComponents(destination: Int) {
        with(binding){

            when (destination) {
                R.id.listFragment ->{
                    toolbar.visibility = View.VISIBLE
                    bottomNavigationView.visibility = View.VISIBLE
                    shouldShowMenu = true
                }
                R.id.loginFragment -> {
                    toolbar.visibility = View.GONE
                    bottomNavigationView.visibility = View.GONE
                    shouldShowMenu = false
                }

                R.id.registerFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavigationView.visibility = View.GONE
                    shouldShowMenu = false
                }
                R.id.newBookFragment -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavigationView.visibility = View.GONE
                    shouldShowMenu = false
                }

                else -> {
                    toolbar.visibility = View.VISIBLE
                    bottomNavigationView.visibility = View.VISIBLE
                    shouldShowMenu = false
                }
            }
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nav, menu)
        return true
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        // Controla a visibilidade do item do menu com base na variável shouldShowMenu
        menu?.findItem(R.id.newBookFragment)?.isVisible = shouldShowMenu
        Log.d("MenuVisibility", "shouldShowMenu: $shouldShowMenu")
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.newBookFragment -> {
                navController.navigate(R.id.action_listFragment_to_newBookFragment)// Ação para o item de configurações
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}