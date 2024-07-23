package com.team1.simplebank.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.synrgy.xdomain.model.AccountModel
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
        binding.btnChangeAccount.setOnClickListener{
            Toast.makeText(requireContext(), "Ganti Akun", Toast.LENGTH_SHORT).show()
        }


        viewModel.userAccountsDetailsInProfileFragment.observe(viewLifecycleOwner){value->
            when(value){
                is ResourceState.Loading ->{}
                is ResourceState.Success -> {
                    val data = value.data[0]
                    showAccountsDetails(data)

                }
                is ResourceState.Error -> {
                    Log.d("error state", "onError = ${value.exception}")
                    Toast.makeText(requireContext(), value.exception, Toast.LENGTH_SHORT).show()
                }
                ResourceState.Idle -> {}
            }
        }
        viewModel.isShowMoreOrLessVisible.observe(viewLifecycleOwner) {
            showMoreOrLessInformation(it)
        }
        setupOnClickListener()

    }

    private fun setupOnClickListener(){
        binding.btnHideFullAccountNumber.setOnClickListener {
            viewModel.toggleShowMoreOrLessInformation(true)
        }

        binding.btnShowFullAccountNumber.setOnClickListener {
            viewModel.toggleShowMoreOrLessInformation(false)
        }
    }

    private fun showMoreOrLessInformation(visibility: Boolean) {
        with(binding) {
            btnHideFullAccountNumber.visibility = if (visibility) View.GONE else View.VISIBLE
            hideFullAccountNumber.visibility = if (visibility)View.GONE else View.VISIBLE
            showFullAccountNumber.visibility = if (visibility) View.VISIBLE else View.GONE
            btnShowFullAccountNumber.visibility = if (visibility) View.VISIBLE else View.GONE
        }
    }

    private fun showAccountsDetails(input:AccountModel){
        with(binding){
            cardHolderName.text = input.fullName
            expiredDate.text = input.expDate
            accountType.text = input.accountType
            accountName.text = input.fullName
            accountNumber.text = input.noAccount
        }
    }

}