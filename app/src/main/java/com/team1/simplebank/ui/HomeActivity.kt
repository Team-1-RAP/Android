package com.team1.simplebank.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team1.simplebank.R
import com.team1.simplebank.databinding.ActivityHomeBinding
import com.team1.simplebank.ui.auth.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.checkSession().observe(this) { loginData ->
            if (loginData.accessToken == "") {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        super.onCreate(savedInstanceState)


        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)

    }

    //clear session when app is idle for 10 minutes
    override fun onStop() {
        super.onStop()
        //TODO: implements
    }

    //clear session when app is closed
    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearDataStore()
    }
}