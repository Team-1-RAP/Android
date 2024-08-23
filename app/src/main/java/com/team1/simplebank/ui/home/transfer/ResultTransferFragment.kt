package com.team1.simplebank.ui.home.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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

    }

    private fun setupDataUI(data: ResultTransferModel) {
        with(binding) {
            nameAccountNumberDestination.text = data.recipientFullName
            nameBankAndAccountNumber.text = getString(
                R.string.destination_bank_and_account_number,
                data.recipientBankName,
                data.recipientBankNoAccount
            )
            theNumberOfTransaction.text = getString(R.string.balance_format, data.amount.toRupiah())

            nameAccountNumberSource.text = data.sourceFullName
            nameBankAndAccountNumberSource.text = getString(
                R.string.destination_bank_and_account_number,
                data.sourceBankName,
                data.sourceAccount
            )
            idTransaction.text = data.transactionId

        }
    }
}