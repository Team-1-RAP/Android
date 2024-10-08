package com.team1.simplebank.ui.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.synrgy.xdomain.model.AccountModel
import com.team1.simplebank.R
import com.team1.simplebank.common.handler.ResourceState
import com.team1.simplebank.databinding.CustomAlertDialogBinding
import com.team1.simplebank.databinding.FragmentProfileBinding
import com.team1.simplebank.ui.WelcomeActivity
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
            showLogOutConfirmationDialog()
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
                    copyAccountNumber(data.noAccount)

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

    private fun copyAccountNumber(inputAccountNumber:String){
        val clipBoardManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("accountNumber",inputAccountNumber)
        binding.btnCopyNumberAccountProfile.setOnClickListener {
            Snackbar.make(binding.root,"Copy To Clipboard",Snackbar.LENGTH_SHORT).show()
            clipBoardManager.setPrimaryClip(clipData)
        }
    }

    private fun showAccountsDetails(input:AccountModel){
        with(binding){
            cardHolderName.text = input.fullName
            expiredDate.text = input.expDate
            accountType.text = input.accountType
            accountName.text = input.fullName
            accountNumber.text = input.noAccount

            //description
            cardHolderName.contentDescription = input.fullName
            expiredDate.contentDescription = input.expDate
            accountType.contentDescription = input.accountType
            accountName.contentDescription = input.fullName
            accountNumber.contentDescription = input.noAccount
        }
    }


    private fun showLogOutConfirmationDialog() {
        val dialogView = CustomAlertDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .create()

        dialogView.alertTitle.text = getString(R.string.konfirmasi)
        dialogView.alertMessage.text = getString(R.string.apakah_anda_benar_benar_ingin_logout_dari_akun_anda)
        dialogView.dismissButtonDialog.apply {
            contentDescription = getString(R.string.dismisButtonLabel)
            text = getString(R.string.dismisButtonLabel)
            setOnClickListener { alertDialog.dismiss() }
        }
        dialogView.actionButtonDialog.apply {
            contentDescription = getString(R.string.actionButtonLabel)
            text = getString(R.string.actionButtonLabel)
            setOnClickListener {
                viewModel.logout()
                startActivity(Intent(requireContext(), WelcomeActivity::class.java))
                requireActivity().finish()
                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }
}