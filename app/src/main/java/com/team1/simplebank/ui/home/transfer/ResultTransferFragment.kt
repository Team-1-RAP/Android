package com.team1.simplebank.ui.home.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.synrgy.xdomain.model.ResultTransferModel
import com.team1.simplebank.R
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.common.utils.Converter.toRupiah
import com.team1.simplebank.databinding.FragmentResultTransferBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ResultTransferFragment : Fragment() {
    private lateinit var binding: FragmentResultTransferBinding
    private val viewModel: TransferSharedViewModel by activityViewModels<TransferSharedViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentResultTransferBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataResultTransfer.collect {
                    when (it) {
                        ResourceState.Loading -> {
                            binding.progresbarTransfer.visibility = View.VISIBLE
                        }

                        is ResourceState.Success -> {
                            binding.progresbarTransfer.visibility = View.GONE
                            setupDataUI(it.data)
                        }

                        is ResourceState.Error -> {
                            Toast.makeText(requireContext(), "${it.exception}", Toast.LENGTH_SHORT)
                                .show()
                        }

                        ResourceState.Idle -> {
                            //do nothing
                        }

                    }
                }
            }
        }

        btnBackClicked()
    }

    private fun setupDataUI(data: ResultTransferModel) {
        with(binding) {
            nameAccountNumberDestination.text = data.recipientFullName
            nameAccountNumberDestination.contentDescription = data.recipientFullName

            timeResultConfirmationTransfer.text = getString(R.string.timeResultFormat, data.time, "WIB")
            timeResultConfirmationTransfer.contentDescription = getString(R.string.timeResultFormat, data.time, "WIB")

            dateResultConfirmationTransfer.text = data.date
            dateResultConfirmationTransfer.contentDescription = data.date

            nameBankAndAccountNumber.text = getString(
                R.string.destination_bank_and_account_number,
                data.recipientBankName,
                data.recipientBankNoAccount
            )
            nameBankAndAccountNumber.contentDescription = getString(
                R.string.destination_bank_and_account_number,
                data.recipientBankName,
                data.recipientBankNoAccount
            )

            theNumberOfTransaction.text = getString(R.string.balance_format, data.amount.toRupiah())
            theNumberOfTransaction.contentDescription = getString(R.string.balance_format, data.amount.toRupiah())

            nameAccountNumberSource.text = data.sourceFullName
            nameAccountNumberSource.contentDescription = data.sourceFullName
            nameBankAndAccountNumberSource.text = getString(
                R.string.destination_bank_and_account_number,
                data.sourceBankName,
                data.sourceAccount
            )
            nameBankAndAccountNumberSource.contentDescription = getString(
                R.string.destination_bank_and_account_number,
                data.sourceBankName,
                data.sourceAccount
            )
            idTransaction.text = data.transactionId
            idTransaction.contentDescription = data.transactionId

        }
    }

    private fun btnBackClicked(){
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack(R.id.navigation_home, false)
                }
            })
    }

}