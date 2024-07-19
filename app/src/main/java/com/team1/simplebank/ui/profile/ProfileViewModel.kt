package com.team1.simplebank.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy.xdomain.useCase.auth.ClearSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearSessionUseCase: ClearSessionUseCase,
): ViewModel() {

    fun logout() {
        viewModelScope.launch {
            clearSessionUseCase.invoke()
        }
    }
}