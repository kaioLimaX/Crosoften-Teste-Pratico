package com.skymob.crosoftenteste.presentation.ui

import android.os.Bundle
import android.util.Log
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
    private lateinit var navHostFragment : NavHostFragment
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews(binding)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { // ID do botão de voltar na Toolbar
                Log.i("_clique", "onOptionsItemSelected: ")
                onBackPressed() // Volta para o fragmento anterior
                true // Indica que o evento foi tratado
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setVisibilityForNavigationAndToolbar(isBottomNavVisible: Boolean, isToolbarVisible: Boolean) {
        binding.bottomNavigation.visibility = if (isBottomNavVisible) View.VISIBLE else View.GONE

    }

    private fun initViews(binding: ActivityMainBinding) {

        navHostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController



        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> setVisibilityForNavigationAndToolbar(
                    isBottomNavVisible = false,
                    isToolbarVisible = false
                )
                R.id.registerFragment -> setVisibilityForNavigationAndToolbar(
                    isBottomNavVisible = false,
                    isToolbarVisible = true
                )
                R.id.detailsFragment -> setVisibilityForNavigationAndToolbar(
                    isBottomNavVisible = false,
                    isToolbarVisible = true
                )
                R.id.newBookFragment -> setVisibilityForNavigationAndToolbar(
                    isBottomNavVisible = false,
                    isToolbarVisible = true
                )

                else -> setVisibilityForNavigationAndToolbar(
                    isBottomNavVisible = true,
                    isToolbarVisible = false
                )
            }
            //updateToolbar(destination.id)
        }

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
        }

    }

    /* private fun updateToolbar(destinationId: Int) {
         when (destinationId) {
             R.id.loginFragment -> {
                 supportActionBar?.title = "Login"
                 supportActionBar?.setDisplayHomeAsUpEnabled(false)
             }
             R.id.registerFragment -> {
                 supportActionBar?.title = "Registrar"
                 supportActionBar?.setDisplayHomeAsUpEnabled(true)
             }
             else -> {
                 supportActionBar?.setDisplayHomeAsUpEnabled(false)
             }
         }
     }*/
}