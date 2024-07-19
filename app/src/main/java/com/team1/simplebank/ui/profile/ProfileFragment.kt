package com.team1.simplebank.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.team1.simplebank.R

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val viewModel: ProfileViewModel by viewModels()
        val btnLogOut = view?.findViewById<Button>(R.id.btn_logout)
        btnLogOut?.setOnClickListener {
            viewModel.logout()
        }

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

}