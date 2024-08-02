package com.team1.simplebank.ui.account_mutation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.xdomain.model.MutationDataUI
import com.team1.simplebank.adapter.MutationPagerAdapter
import com.team1.simplebank.databinding.FragmentAccountMutationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountMutationFragment : Fragment() {

    private lateinit var binding: FragmentAccountMutationBinding
    private val accountMutationViewModel: AccountMutationViewModel by viewModels()
    private var adapter: MutationPagerAdapter? = null
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
        //collectDataUI("3737657598213562", 1)
        accountMutationViewModel.getDataWithoutPagination("3737657598213562", 7)
        collectDataUIWithoutPagination()
        initRecyclerview()
    }

    private fun initRecyclerview() {
        binding.rvResultTransaction.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = MutationPagerAdapter()
        binding.rvResultTransaction.adapter = adapter
        //adapter?.submitList(provideDataManual())

    }

    /*private fun collectDataUI(
        inputDataNoAccount: String,
        inputDataMont: Int,
        inputType: String? = null
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            accountMutationViewModel.dataMutationUI(inputDataNoAccount, inputDataMont, inputType)
                .collectLatest { pagingData ->
                    adapter?.submitData(pagingData)
                }
        }
    }*/

    private fun collectDataUIWithoutPagination() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                accountMutationViewModel.dataMutationUI.collect {
                    adapter?.submitList(it)
                }
            }
        }
    }

    private fun provideDataManual(): List<MutationDataUI> {
        return listOf(
            MutationDataUI.Header("2020"),
            MutationDataUI.Item(
                "Transfer",
                "PENGELUARAN",
                "AZISZ",
                "BCA",
                20000,
                "32010101",
                "SUKSES"
            ),
            MutationDataUI.Item(
                "Transfer",
                "PENGELUARAN",
                "DAFRIZA",
                "BCA",
                20200,
                "32010101",
                "SUKSES"
            ),
            MutationDataUI.Item(
                "Transfer",
                "PENGELUARAN",
                "DINDA",
                "BCA",
                20100,
                "32010101",
                "SUKSES"
            ),
            MutationDataUI.Header("2021"),
            MutationDataUI.Item(
                "Transfer",
                "PEMASUKAN",
                "AZISZ",
                "BCA",
                20000,
                "32010101",
                "SUKSES"
            ),


            )
    }

    override fun onResume() {
        super.onResume()
        accountMutationViewModel.getDataWithoutPagination("3737657598213562", 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}