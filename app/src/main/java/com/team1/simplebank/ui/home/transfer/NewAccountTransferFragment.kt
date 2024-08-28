package com.team1.simplebank.ui.home.transfer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.synrgy.xdomain.model.SourceAccountModel
import com.team1.simplebank.R
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.databinding.FragmentNewAccountTransferBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class NewAccountTransferFragment : Fragment() {

    private lateinit var binding: FragmentNewAccountTransferBinding
    private val viewModel: TransferSharedViewModel by activityViewModels<TransferSharedViewModel>()
    private var isValidNoAccount: Boolean = false
    private var isValidDescription: Boolean = false

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


        binding.textInputNumberAccountDestination.addTextChangedListener(textWatcher())
        binding.textInputTransferInformation.addTextChangedListener(textWatcher())

        viewModel.dataButtonState.observe(viewLifecycleOwner) {
            Log.d("DataState", "data button state = $it")
            buttonState(it)
        }
        dataAccountChoosing()

        getAllDataSourceChoosing()

       buttonClicked()
    }

    private fun buttonClicked(){
        binding.btnNext.setOnClickListener {
            val noAccount = binding.textInputNumberAccountDestination.text
            binding.textInputNumberAccountDestination.contentDescription = noAccount
            Log.d("noAccount", "onViewCreated: $noAccount")

            viewModel.cekValidationTransfer(
                1,
                noAccount = noAccount.toString(),
                onLoading = { onLoading(it) },
                onResult = { isValid(it) })
        }
        binding.btnSaveAccountNumber.setOnClickListener {
            getResponseDataSavedValidation()
            Snackbar.make(requireContext(),binding.root,"Data Berhasil Disimpan",Snackbar.LENGTH_SHORT).show()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    //digunakan untuk mengisi nilai dari button state, apakah sudah terisi belum untuk button sumber rekening
    // jika belum maka kembali false, jika sudah kembali true
    private fun dataAccountChoosing() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataSourceAccountChoosing.collect {
                    when (it) {
                        ResourceState.Loading -> {

                        }

                        is ResourceState.Success -> {
                            viewModel.setButtonState(isValidNoAccount, isValidDescription)
                        }

                        is ResourceState.Error -> {

                        }

                        ResourceState.Idle -> {
                            viewModel.setButtonState(isValidNoAccount, isValidDescription)
                        }

                    }
                }
            }
        }
    }

    private fun getAllDataSourceChoosing() {

        viewModel.getAllDataSourceAccount.observe(viewLifecycleOwner) {
            when (it) {
                ResourceState.Loading -> {
                    //loading data bila msih ngeload
                }

                is ResourceState.Success -> {
                    spinnerClicked(it.data)
                }

                is ResourceState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Data Sumber Rekening Kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ResourceState.Idle -> {
                    //do nothing
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.dataForSpinner.observe(viewLifecycleOwner) {
            when (it) {
                ResourceState.Loading -> {
                    //nanti loading kalo datanya ga ada

                }

                is ResourceState.Success -> {
                    spinnerSetup(it.data)
                }

                is ResourceState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Data sumber rekening kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ResourceState.Idle -> {
                    //do nothing
                }

            }
        }
    }

    private fun spinnerSetup(dataItems: List<String>) {
        val spinnerAdapterSourceAccount = ArrayAdapter(
            requireContext(),
            R.layout.spinner_list_item,
            dataItems
        )
        binding.autocompleteItemSourceAccount.setAdapter(spinnerAdapterSourceAccount)
        binding.autocompleteItemSourceAccount.setDropDownBackgroundDrawable(resources.getDrawable(R.drawable.custom_pop_up_background))
    }

    private fun spinnerClicked(dataItems: List<SourceAccountModel>) {
        binding.autocompleteItemSourceAccount.setOnItemClickListener { parent, view, position, id ->
            val data = dataItems[position]
            viewModel.setDataSourceAccountChose(
                fullName = data.fullName,
                accountType = data.accountType,
                noAccount = data.noAccount,
            )
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
            val descriptionArgs = binding.textInputTransferInformation.text.toString()
            val action =
                NewAccountTransferFragmentDirections.actionNewAccountTransferFragmentToDetailTransferFragment(
                    descriptionArgs
                )
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Data Salah", Toast.LENGTH_SHORT).show()
        }
    }


    //cek validasi inputan oleh user

    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                when (s) {
                    binding.textInputNumberAccountDestination.text -> {
                        isValidNoAccount = validateInputNoAccount(s)
                        binding.textInputNumberAccountDestination.contentDescription = s.toString()
                        viewModel.cekValidationTransfer(
                            1,
                            s.toString(),
                            onLoading = {},
                            onResult = { stateButtonSaveNoAccount(it) })
                    }

                    binding.textInputTransferInformation.text -> {
                        isValidDescription = validateDescription(s)
                        binding.textInputTransferInformation.contentDescription = s.toString()

                    }
                }

                viewModel.setButtonState(isValidNoAccount, isValidDescription)
            }
        }
    }

    //data setelah save rekening berhasil
    private fun getResponseDataSavedValidation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataValidationTransferSuccess.collect {
                    when (it) {
                        ResourceState.Loading -> {
                            //do nothing
                        }

                        is ResourceState.Success -> {
                            saveDataNoAccountToLocal(
                                it.data.username,
                                it.data.fullName,
                                it.data.bankDestination,
                                it.data.bankId.toInt(),
                                it.data.accountNumber,
                                it.data.adminFee
                            )
                        }

                        is ResourceState.Error -> {

                        }
                        ResourceState.Idle -> {

                        }

                    }
                }
            }
        }
    }

    private fun saveDataNoAccountToLocal(
        username:String,
        fullName: String,
        bankName: String,
        bankId: Int,
        noAccount: String,
        adminFee:Int
    ) {
        viewModel.insertTransferNewAccount(
            userName = username,
            fullName=fullName,
            bankName = bankName,
            bankId = bankId,
            noAccount = noAccount,
            adminFee = adminFee
        )
    }

    private fun stateButtonSaveNoAccount(state: Boolean) {
        with(binding.btnSaveAccountNumber) {
            isEnabled = state
            setBackgroundColor(
                if (state) requireContext().getColor(R.color.primary_color)
                else requireContext().getColor(R.color.white)
            )
            setTextColor(
                if (state) requireContext().getColor(R.color.gray)
                else requireContext().getColor(R.color.primary_color)
            )
        }
    }

    private fun validateInputNoAccount(inputNoAccount: Editable?): Boolean {
        val inputLength = inputNoAccount?.length ?: 0
        val isValid = inputLength >= 4
        binding.textLayoutDestinationNumberAccount.error =
            if (isValid) "" else "Input Must be 4 digits"
        return isValid
    }

    private fun validateDescription(inputDescription: Editable?): Boolean {
        val isValid = inputDescription?.isNotEmpty()!!
        binding.textLayoutTransferInformation.error = if (isValid) "" else "Input can't empty"
        return isValid
    }


    private fun buttonState(isValid: Boolean) {
        with(binding.btnNext) {
            isEnabled = isValid
            setBackgroundColor(
                if (isValid) requireContext().getColor(R.color.primary_color)
                else requireContext().getColor(R.color.disable_color)
            )
        }
    }
}
