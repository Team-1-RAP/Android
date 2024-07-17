package com.team1.simplebank.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.team1.simplebank.R

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val copyButton = view.findViewById<ImageButton>(R.id.btn_copy_account_number)
        copyButton.setOnClickListener {
            Snackbar.make(view, "Nomor Rekening Sudah disalin", Snackbar.LENGTH_SHORT).show()
        }

        val logoutButton = view.findViewById<Button>(R.id.btn_logout)
        logoutButton.setOnClickListener {
            showLogoutConfirmationDialog(view.context)
        }

        val accountSettingButton = view.findViewById<TextView>(R.id.account_setting_title)
        accountSettingButton.setOnClickListener {
        }
    }

    private fun showLogoutConfirmationDialog(context: Context){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah Anda benar benar ingin Logout dari akun anda?")

        builder.setPositiveButton("Iya"){ dialog, id ->
            dialog.dismiss()
        }

        builder.setPositiveButton("Tidak"){ dialog, id ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()

//        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.blue))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.light_blue))
    }

}