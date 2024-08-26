package com.team1.simplebank.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team1.simplebank.R
import com.team1.simplebank.databinding.ActivityHomeBinding
import com.team1.simplebank.ui.auth.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBar: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set toolbar
        //setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        /*appBar = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.navigation_account_mutation,
                R.id.navigation_qris,
                R.id.navigation_profile,
            )
        )*/
        //binding.toolbar.setupWithNavController(navController, appBar)

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.resultTransferFragment){
                navView.visibility = View.GONE
            }else{
                navView.visibility = View.VISIBLE
            }
        }

    }

    //back button in toolbar
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    //clear session when app is idle for 10 minutes
    override fun onStop() {
        super.onStop()
        //TODO: implements
    }

    //clear session when app is closed
    override fun onDestroy() {
        super.onDestroy()
    }
}