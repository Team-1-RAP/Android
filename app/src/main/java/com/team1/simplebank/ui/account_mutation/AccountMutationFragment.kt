package com.team1.simplebank.ui.account_mutation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.xdomain.model.FilterInput
import com.synrgy.xdomain.model.MutationDataUI
import com.team1.simplebank.R
import com.team1.simplebank.adapter.MutationPagerAdapterV2
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.common.utils.Converter.toMonthNumber
import com.team1.simplebank.databinding.FragmentAccountMutationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountMutationFragment : Fragment() {

    private lateinit var binding: FragmentAccountMutationBinding
    private val accountMutationViewModel: AccountMutationViewModel by viewModels()
    private var adapter: MutationPagerAdapterV2? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountMutationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerview()

        accountMutationViewModel.inputFiltering(FilterInput.Month(8))
        accountMutationViewModel.inputFiltering(FilterInput.Type(null))


        spinnerClicked()

        binding.autocompleteItemMonth.setDropDownBackgroundDrawable(resources.getDrawable(R.drawable.custom_pop_up_background))
        binding.autocompleteTypeTranscation.setDropDownBackgroundDrawable(resources.getDrawable(R.drawable.custom_pop_up_background))


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountMutationViewModel.noAccount.collectLatest { value ->
                    if (value != null) {
                        accountMutationViewModel.inputFiltering(FilterInput.NoAccount(value))
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountMutationViewModel.dataMutationOnUIWithFiltering().collectLatest {
                    adapter?.submitData(it)
                }
            }
        }


    }

    private fun spinnerClicked() {
        binding.autocompleteItemMonth.setOnItemClickListener { adapterView, view, i, l ->
            val monthPosition = adapterView.getItemAtPosition(i).toString()
            val month = monthPosition.toMonthNumber()
            accountMutationViewModel.inputFiltering(FilterInput.Month(month))
            Toast.makeText(requireContext(), "$month", Toast.LENGTH_SHORT).show()
        }

        binding.autocompleteTypeTranscation.setOnItemClickListener { adapterView, view, i, l ->
            val type = adapterView.getItemAtPosition(i).toString()
            when (adapterView.getItemAtPosition(i).toString()) {
                "Semua Transaksi" -> {
                    accountMutationViewModel.inputFiltering(FilterInput.Type(null))
                }

                "Pemasukan" -> {
                    accountMutationViewModel.inputFiltering(FilterInput.Type("PEMASUKAN"))
                }

                "Pengeluaran" -> {
                    accountMutationViewModel.inputFiltering(FilterInput.Type("PENGELUARAN"))
                }
            }
            Toast.makeText(requireContext(), type, Toast.LENGTH_SHORT).show()
        }
    }


    private fun initRecyclerview() {
        binding.rvResultTransaction.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        this.adapter = MutationPagerAdapterV2()
        binding.rvResultTransaction.adapter = this.adapter
        //adapter?.submitList(provideDataManual())

    }

    override fun onResume() {
        super.onResume()
        val spinnerMonthNewAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_list_item,
            resources.getStringArray(R.array.months_array)
        )

        val spinnerTransactionNewAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_list_item,
            resources.getStringArray(R.array.transaction_types_array)
        )
        binding.autocompleteItemMonth.setAdapter(spinnerMonthNewAdapter)
        binding.autocompleteTypeTranscation.setAdapter(spinnerTransactionNewAdapter)
    }
}