package com.team1.simplebank.ui.account_mutation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


class AccountMutationViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is account_mutation Fragment"
    }
    val text: LiveData<String> = _text
}