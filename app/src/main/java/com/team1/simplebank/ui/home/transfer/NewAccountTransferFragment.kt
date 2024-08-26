package com.team1.simplebank.ui.home.transfer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.synrgy.xdomain.model.SourceAccountModel
import com.team1.simplebank.R
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.databinding.FragmentNewAccountTransferBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewAccountTransferFragment : Fragment() {

    private lateinit var binding: FragmentNewAccountTransferBinding
    private val viewModel: TransferSharedViewModel by activityViewModels<TransferSharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentNewAccountTransferBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnNext.setOnClickListener {
            val noAccount = binding.textInputNumberAccountDestination.text
            Log.d("noAccount", "onViewCreated: $noAccount")

            viewModel.cekValidationTransfer(
                1,
                noAccount = noAccount.toString(),
                onLoading = { onLoading(it) },
                onResult = { isValid(it) })
        }


        viewModel.getAllDataSourceAccount.observe(viewLifecycleOwner){
            when(it){
                ResourceState.Loading -> {
                    //loading data bila msih ngeload
                }
                is ResourceState.Success -> {
                    spinnerClicked(it.data)
                }
                is ResourceState.Error -> {
                    Toast.makeText(requireContext(), "Data Sumber Rekening Kosong", Toast.LENGTH_SHORT).show()
                }
                ResourceState.Idle -> {
                    //do nothing
                }

            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getDataForSourceAccountSpinner.observe(viewLifecycleOwner){
            when(it){
                ResourceState.Loading -> {
                    //nanti loading kalo datanya ga ada
                }
                is ResourceState.Success -> {
                    spinnerSetup(it.data)
                }
                is ResourceState.Error -> {
                    Toast.makeText(requireContext(), "Data sumber rekening kosong", Toast.LENGTH_SHORT).show()
                }
                ResourceState.Idle -> {
                    //do nothing
                }

            }
        }
    }

    private fun spinnerSetup(dataItems:List<String>){
        val spinnerAdapterSourceAccount = ArrayAdapter(
            requireContext(),
            R.layout.spinner_list_item,
            dataItems
        )
        binding.autocompleteItemSourceAccount.setAdapter(spinnerAdapterSourceAccount)
        binding.autocompleteItemSourceAccount.setDropDownBackgroundDrawable(resources.getDrawable(R.drawable.custom_pop_up_background))

    }

    private fun spinnerClicked(dataItems:List<SourceAccountModel>) {
        binding.autocompleteItemSourceAccount.setOnItemClickListener { parent, view, position, id ->
            val data = dataItems[position]
            viewModel.setDataSourceAccountChose(fullName = data.fullName, accountType = data.accountType, noAccount = data.noAccount,)
            Toast.makeText(requireContext(), data.noAccount, Toast.LENGTH_SHORT).show()
        }
    }


    //Fungsi untuk mengisi callback apabila data memasuki status loading
    private fun onLoading(state: Boolean) {
        if (state) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }

    // Fungsi untuk mengisi callback apabila data telah melawati status loading
    private fun isValid(state: Boolean) {
        if (state) {
            findNavController().navigate(R.id.action_newAccountTransferFragment_to_detailTransferFragment)
        } else {
            Toast.makeText(requireContext(), "Data Salah", Toast.LENGTH_SHORT).show()
        }
    }
}
