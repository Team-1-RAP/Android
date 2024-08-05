package com.team1.simplebank.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.synrgy.xdomain.model.AccountModel
import com.synrgy.xdomain.model.Menu
import com.team1.simplebank.R
import com.team1.simplebank.adapter.MenuBerandaAdapter
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.common.utils.Converter.toRupiah
import com.team1.simplebank.databinding.FragmentHomeBinding
import com.team1.simplebank.ui.HomeActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeActivityViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        return FragmentHomeBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userAccount.collect {
                    when (it) {
                        is ResourceState.Loading -> {
                            binding.progressbar.visibility = View.VISIBLE
                            Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                        }

                        is ResourceState.Success -> {
                            isDataSuccess(it.data[0])
                        }

                        is ResourceState.Error -> {
                            Toast.makeText(requireContext(), it.exception, Toast.LENGTH_SHORT)
                                .show()
                        }

                        is ResourceState.Idle -> {}
                    }
                }
            }
        }*/

        //data user, saldo, nomor rekening
        viewModel.userAccountsDetails.observe(viewLifecycleOwner) {
            when (it) {
                is ResourceState.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                is ResourceState.Success -> {
                    val data = it.data[0]
                    isDataSuccess(data)
                    copyNumberAccount(data.cardNumber)
                }

                is ResourceState.Error -> {
                    Toast.makeText(requireContext(), it.exception, Toast.LENGTH_SHORT).show()
                }

                is ResourceState.Idle -> {}
            }
        }
        //state UI untuk tombol tampilkan lebih dan sedikit

        viewModel.isShowOrHideBalanceValue.observe(viewLifecycleOwner){
            showOrHideBalance(it)
        }

        showRecyclerView()
        setUpClickListener()

    }

    //kumpulan tombol
    private fun setUpClickListener() {
        binding.btnHelpdesk.setOnClickListener {
            Toast.makeText(requireContext(), "Helpdesk", Toast.LENGTH_SHORT).show()
        }
        binding.btnShowOrHideBalance.setOnClickListener{
            with(binding){
                val isState = accountBalance2.visibility==View.GONE
                viewModel.toggleShowOrHideBalance(isState)
            }
        }

    }

    private fun showOrHideBalance(input:Boolean){
        with(binding){
            accountBalance2.visibility = if(input) View.VISIBLE else View.GONE
            hideAccountBalance.visibility = if (input) View.GONE else View.VISIBLE
            btnShowOrHideBalance.setImageResource(if (input) R.drawable.show_logo else R.drawable.hide_logo)
        }
    }

    //copy nomor rekening
    private fun copyNumberAccount(data: String) {
        val clipBoardManager =
            requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("accountNumber", data)
        binding.btnCopyNumberAccount.setOnClickListener {
            Snackbar.make(binding.root, "Copy to Clipboard", Snackbar.LENGTH_SHORT).show()
            clipBoardManager.setPrimaryClip(clipData)
        }
    }

    //menampilkan data ketika berhasil mengambil data dari API
    private fun isDataSuccess(listData: AccountModel) {
        listData.let {
            binding.apply {
                progressbar.visibility = View.GONE
                heyTitle.visibility=View.VISIBLE
                userFullName.apply {
                    visibility = View.VISIBLE
                    text = listData.fullName
                }
                layoutAccountNumber.visibility = View.VISIBLE
                numberAccount2.text = getString(R.string.number_account, listData.cardNumber)
                layoutAccountBalance.visibility = View.VISIBLE
                accountBalance2.text = listData.balance.toRupiah()

            }
        }
    }


    //menampilkan recyclerview
    private fun showRecyclerView() {
        binding.rvMenu.setHasFixedSize(true)
        binding.rvMenu.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.rvMenu.animation = null
        viewModel.addDataRecyclerView(resources)

        viewModel.listData.observe(viewLifecycleOwner) { data ->
            val adapter = MenuBerandaAdapter(data) {
                clicked(it)
            }
            binding.rvMenu.adapter = adapter
        }
    }

    //tombol menu beranda
    private fun clicked(idMenu: Menu) {
        when (idMenu.id) {
            1 -> findNavController().navigate(R.id.action_navigation_home_to_cashWithDrawlFragment)
            2 -> Toast.makeText(
                requireContext(),
                R.string.cash_deposit_title,
                Toast.LENGTH_SHORT
            )
                .show()

            3 -> Toast.makeText(requireContext(), R.string.transfer_title, Toast.LENGTH_SHORT)
                .show()

            4 -> findNavController().navigate(R.id.action_navigation_home_to_virtualAccountFragment)
            5 -> Toast.makeText(requireContext(), R.string.gold_tube_title, Toast.LENGTH_SHORT)
                .show()

            6 -> Toast.makeText(requireContext(), R.string.pay_title, Toast.LENGTH_SHORT).show()
            7 -> findNavController().navigate(R.id.action_navigation_home_to_topUpEWalletFragment)
            8 -> Toast.makeText(requireContext(), R.string.etc_title, Toast.LENGTH_SHORT)
                .show()

        }
    }
}



