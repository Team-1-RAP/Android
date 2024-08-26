package com.team1.simplebank.ui.home.transfer

import android.os.Bundle
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
import com.synrgy.xdomain.model.ConfirmationTransferModel
import com.team1.simplebank.R
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.common.utils.Converter.toRupiah
import com.team1.simplebank.common.utils.splitWordIntoOneLetter
import com.team1.simplebank.databinding.FragmentConfirmationTransferBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConfirmationTransferFragment : Fragment() {

    private lateinit var binding: FragmentConfirmationTransferBinding
    private val viewModel: TransferSharedViewModel by activityViewModels<TransferSharedViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentConfirmationTransferBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_confirmationTransferFragment_to_resultTransferFragment)
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mergeAllDataTransfer.collect {
                    when (it) {
                        ResourceState.Loading -> {
                            //do loading state
                        }

                        is ResourceState.Success -> {
                            Log.d("Succes MergeAllData", " ${it.data}")
                            setupDataUI(it.data)
                            btnTransferClicked(it.data)

                        }

                        is ResourceState.Error -> {
                            Log.d("Error MergeAllData", " ${it.exception}")
                            Toast.makeText(requireContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show()
                        }

                        ResourceState.Idle -> {
                            //do nothing
                        }

                    }
                }
            }
        }
    }

    private fun setupDataUI(item: ConfirmationTransferModel) {
        with(binding) {
            iconUsernameSourceNumberAccount.text = splitWordIntoOneLetter(item.fullUserNameSender)
            transferSourceAccountUsername.text = item.fullUserNameSender
            sourceAccountNumberAndDestinationBank.text = getString(
                R.string.destination_bank_and_account_number,
                item.accountTypeSender,
                item.accountNumberSender
            )

            iconUsernameDestinationNumberAccount.text = splitWordIntoOneLetter(item.fullName)
            transferDestinationAccountUsername.text = item.fullName
            destinationAccountNumberAndDestinationBank.text = getString(
                R.string.destination_bank_and_account_number,
                item.bankDestination,
                item.accountNumber
            )

            nominalTransfer.text = getString(R.string.balance_format, item.totalTransfer.toRupiah())
            feeAdmin.text = getString(R.string.balance_format, item.adminFee.toRupiah())
            totalTransfer.text =
                getString(R.string.balance_format, item.totalTransferWithAdmin.toRupiah())

        }
    }

    private fun btnTransferClicked(data: ConfirmationTransferModel) {
        binding.btnTransfer.setOnClickListener {
            val inputPin = binding.textInputPasswordTransaction.text.toString()
            transferInput(inputPin, data)

        }
    }

    private fun transferInput(inputPin: String, data: ConfirmationTransferModel) {
        val description = data.description ?: "KIRIM UANG"
        viewModel.transfer(
            accountNo = data.accountNumberSender,
            recipientAccountNo = data.accountNumber,
            recipientBankName = data.bankDestination,
            amount = data.totalTransferWithAdmin,
            pin = inputPin,
            description = description,
            onLoading = { onLoading(it) },
            onResult = { onResult(it) }
        )
    }

    private fun onLoading(state: Boolean) {
        if (state) {
            binding.progressbarTranfer.visibility = View.VISIBLE
        } else {
            binding.progressbarTranfer.visibility = View.GONE
        }
    }

    private fun onResult(state: Boolean) {
        if (state) {
            findNavController().navigate(R.id.action_confirmationTransferFragment_to_resultTransferFragment)
        } else {
            Toast.makeText(requireContext(), "Tidak dapat diproses", Toast.LENGTH_SHORT).show()
        }
    }


}