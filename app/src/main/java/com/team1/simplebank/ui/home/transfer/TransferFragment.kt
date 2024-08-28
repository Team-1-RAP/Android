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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.synrgy.xdomain.model.DataUserDestinationLocalModel
import com.team1.simplebank.R
import com.team1.simplebank.adapter.DataNoAccountLocalAdapter
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.databinding.FragmentTransferBinding
import kotlinx.coroutines.launch

class TransferFragment : Fragment() {

    private lateinit var binnding: FragmentTransferBinding
    private var dataNoAccountLocalAdapter: DataNoAccountLocalAdapter? = null
    private val viewModel: TransferSharedViewModel by activityViewModels<TransferSharedViewModel>()
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

        viewModel.userAccountsDetailsInProfileFragment()
        dataNoAccountLocalAdapter = DataNoAccountLocalAdapter(itemClicked = { itemClicked(it) },
            btnDeleteClicked = { btnDeleteClicked(it) })

        setupData()
        setupRecyclerView()


        binnding.btnNewUserTransfer.setOnClickListener {
            findNavController().navigate(R.id.action_transferFragment_to_newAccountTransferFragment)
        }
        binnding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAllDataAccountTransferLocal.collect {
                    when (it) {
                        ResourceState.Loading -> {
                            binnding.progressbar.visibility = View.VISIBLE
                        }

                        is ResourceState.Success -> {
                            binnding.progressbar.visibility = View.GONE
                            dataNoAccountLocalAdapter?.submitList(it.data)
                        }

                        is ResourceState.Error -> {
                            Snackbar.make(requireContext(),binnding.root,"Data tidak ada",Snackbar.LENGTH_SHORT).show()
                        }

                        ResourceState.Idle -> {
                            //do nothing
                        }

                    }
                }
            }
        }

        viewModel.userAccountSource.observe(viewLifecycleOwner) {
            when (it) {
                ResourceState.Loading -> {
                    //do nothing
                }

                is ResourceState.Success -> {
                    val accountSource = it.data[0]
                    viewModel.setDataSourceAccountChose(
                        fullName = accountSource.fullName,
                        accountType = accountSource.accountType,
                        noAccount = accountSource.noAccount
                    )
                }

                is ResourceState.Error -> {
                    Log.d("error state", "onError = ${it.exception}")
                    Snackbar.make(
                        requireContext(),
                        binnding.root,
                        "${it.exception}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                ResourceState.Idle -> {
                    //do nothing
                }

            }
        }
    }

    private fun setupRecyclerView() {
        binnding.rvUserTransferList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binnding.rvUserTransferList.setHasFixedSize(true)
        binnding.rvUserTransferList.adapter = dataNoAccountLocalAdapter
    }

    private fun itemClicked(data: DataUserDestinationLocalModel) {
        //harus pakek viewmodel pindahnya oke
        findNavController().navigate(R.id.action_transferFragment_to_detailTransferFragment).also {
            viewModel.setDataNoAccountDestinationToLocalDB(data)
        }
    }

    private fun btnDeleteClicked(data: String) {
        viewModel.deleteItemNoAccount(data)
        Snackbar.make(requireContext(), binnding.root, "Data Dihapus", Snackbar.LENGTH_SHORT).show()
    }


}