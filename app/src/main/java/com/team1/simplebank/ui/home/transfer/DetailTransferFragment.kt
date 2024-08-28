package com.team1.simplebank.ui.home.transfer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.synrgy.xdomain.model.ValidationTransferModel
import com.team1.simplebank.R
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.common.utils.splitWordIntoOneLetter
import com.team1.simplebank.databinding.FragmentDetailTransferBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailTransferFragment : Fragment() {

    private val args: DetailTransferFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailTransferBinding
    private val viewModel: TransferSharedViewModel by activityViewModels<TransferSharedViewModel>()
    private var isValidNoAccount: Boolean = false
    private var isValidDescription: Boolean = false

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

        binding.textInputTransferTotalAmount.addTextChangedListener(textWatcher())
        binding.textInputTransferInformation.addTextChangedListener(textWatcher())


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

        stateButton(isValidNoAccount && isValidDescription)
        btnClicked()

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnBackBottomDetailTransfer.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun btnClicked() {
        binding.btnNext.setOnClickListener {
            val totalTransferInput = binding.textInputTransferTotalAmount.text.toString()
            val description = binding.textInputTransferInformation.text.toString()
            if (totalTransferInput.isEmpty() && description.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Nominal Kosong dan description kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.merAllDataTransfer(totalTransferInput.toInt(), description)
                findNavController().navigate(R.id.action_detailTransferFragment_to_confirmationTransferFragment)
            }
        }
    }

    private fun setupDataDestinationBank(data: ValidationTransferModel) {
        with(binding){
            iconUsernameDestinationBank.text = splitWordIntoOneLetter(data.fullName)
            iconUsernameDestinationBank.contentDescription = splitWordIntoOneLetter(data.fullName)
            transferAccountUsername.text = data.fullName
            transferAccountUsername.contentDescription = data.fullName
            accountNumberAndDestinationBank.text = getString(
                R.string.destination_bank_and_account_number,
                data.bankDestination,
                data.accountNumber
            )
            accountNumberAndDestinationBank.contentDescription = getString(
                R.string.destination_bank_and_account_number,
                data.bankDestination,
                data.accountNumber
            )
            val value = args.descriptionArgs
            if (value != null) {
                textInputTransferInformation.setText(value)
                textInputTransferInformation.contentDescription = value
            }
        }



    }

    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                when (s) {
                    binding.textInputTransferTotalAmount.text -> {
                        isValidNoAccount = validateInputNoAccount(s)
                    }

                    binding.textInputTransferInformation.text -> {
                        isValidDescription = validateDescription(s)
                    }
                }

                stateButton(isValidNoAccount && isValidDescription)
            }
        }
    }

    private fun validateInputNoAccount(inputNoAccount: Editable?): Boolean {
        val inputLength = inputNoAccount?.length ?: 0
        return if (inputLength > 0){
            val valueInput = if (inputNoAccount.toString() == "") 0 else inputNoAccount.toString().toInt()
            val isValid = valueInput >= 10000
            binding.textLayoutTransferTotalAmount.error = if (isValid) "" else "Minimal Transfer Rp.10.000,00"
            isValid
        }else{
            false
        }
    }

    private fun validateDescription(inputDescription: Editable?): Boolean {
        val isValid = inputDescription?.isNotEmpty()!!
        binding.textLayoutTransferInformation.error = if (isValid) "" else "Input can't empty"
        return isValid
    }

    private fun stateButton(isValid: Boolean) {
        with(binding.btnNext) {
            isEnabled = isValid
            setBackgroundColor(
                if (isValid) requireContext().getColor(R.color.primary_color)
                else requireContext().getColor(R.color.disable_color)
            )
        }
    }

}