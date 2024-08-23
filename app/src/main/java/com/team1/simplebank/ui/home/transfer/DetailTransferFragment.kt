package com.team1.simplebank.ui.home.transfer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.synrgy.xdomain.model.ValidationTransferModel
import com.team1.simplebank.R
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.common.utils.splitWordIntoOneLetter
import com.team1.simplebank.databinding.FragmentDetailTransferBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailTransferFragment : Fragment() {

    private lateinit var binding: FragmentDetailTransferBinding
    private val viewModel: TransferSharedViewModel by activityViewModels<TransferSharedViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentDetailTransferBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataValidationTransferSuccess.collect {
                    when (it) {
                        ResourceState.Loading -> {
                            //lakuin loading nanti apabila datanya masih muter2
                        }

                        is ResourceState.Success -> {
                            setupDataDestinationBank(it.data)
                        }

                        is ResourceState.Error -> {
                            //kirim toast/snackbar kalo datanya gabisa tampil
                        }

                        ResourceState.Idle -> {
                            //gaperlu lakuin apapun
                        }

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataSourceAccountChoosing.collect {
                    when (it) {
                        ResourceState.Loading -> {
                            //loading datanya
                        }

                        is ResourceState.Success -> {
                            Log.d("DetailTransferFragment", "${it.data}")
                        }

                        is ResourceState.Error -> {
                            Log.d("ErrorDetailTransferFragment", "${it.exception}")
                        }

                        ResourceState.Idle -> {}

                    }
                }
            }
        }

        btnClicked()

    }

    private fun btnClicked() {
        binding.btnNext.setOnClickListener {
            val totalTransferInput = binding.textInputTransferTotalAmount.text.toString()
            viewModel.merAllDataTransfer(totalTransferInput.toInt())
            findNavController().navigate(R.id.action_detailTransferFragment_to_confirmationTransferFragment)
        }

        binding.btnBack.setOnClickListener {

        }


    }

    private fun setupDataDestinationBank(data: ValidationTransferModel) {
        binding.iconUsernameDestinationBank.text = splitWordIntoOneLetter(data.fullName)
        binding.transferAccountUsername.text = data.fullName
        binding.accountNumberAndDestinationBank.text = getString(
            R.string.destination_bank_and_account_number,
            data.bankDestination,
            data.accountNumber
        )
    }

}