package com.team1.simplebank.ui

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.team1.simplebank.R
import com.team1.simplebank.databinding.ActivityHomeBinding
import com.team1.simplebank.ui.auth.LoginViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        navView.setupWithNavController(navController)

//        viewModel.checkSession().observe(this){ loginData ->
//            if(loginData.accessToken == "") {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            } else {
//
//                val navView: BottomNavigationView = binding.navView
//
//                val navController = findNavController(R.id.nav_host_fragment_activity_home)
//
//                navView.setupWithNavController(navController)
//            }
//        }
    }
}