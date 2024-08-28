package com.team1.simplebank.ui.home.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.team1.simplebank.R
import com.team1.simplebank.databinding.FragmentTransferBinding

class TransferFragment : Fragment() {

    private lateinit var binnding: FragmentTransferBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentTransferBinding.inflate(inflater, container, false).also {
            binnding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binnding.btnNewUserTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_transferFragment_to_newAccountTransferFragment)
        }
        binnding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }



}