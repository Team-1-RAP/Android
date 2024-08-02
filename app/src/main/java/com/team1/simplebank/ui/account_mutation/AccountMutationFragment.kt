package com.team1.simplebank.ui.account_mutation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
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
    private var adapter : MutationPagerAdapter?=null
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
        binding.rvResultTransaction.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        collectDataUI("3737657598213562", 1,)
        initRecyclerview()
    }

    private fun initRecyclerview(){
        adapter = MutationPagerAdapter()
        binding.rvResultTransaction.adapter = adapter
    }

    private fun collectDataUI(
        inputDataNoAccount: String,
        inputDataMont: Int,
        inputType: String? = null
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            accountMutationViewModel.dataMutationUI(inputDataNoAccount,inputDataMont,inputType).collectLatest { pagingData->
                adapter?.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}