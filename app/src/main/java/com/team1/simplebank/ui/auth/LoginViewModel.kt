package com.team1.simplebank.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.synrgy.xdomain.model.AuthModel
import com.synrgy.xdomain.useCase.auth.ClearSessionUseCase
import com.synrgy.xdomain.useCase.auth.GetSessionUseCase
import com.synrgy.xdomain.useCase.auth.LoginUseCase
import com.team1.simplebank.common.handler.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getSessionUseCase: GetSessionUseCase,
    private val clearSessionUseCase: ClearSessionUseCase,
) : ViewModel() {

    private val _authData = MutableStateFlow<ResourceState<AuthModel>>(ResourceState.Idle)
    val authData = _authData.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            loginUseCase.execute(username, password)
                .collect{
                    when (it) {
                        is ResourceState.Loading -> {
                            _authData.value = ResourceState.Loading
                        }
                        is ResourceState.Success -> {
                            _authData.value = ResourceState.Success(it.data)
                        }
                        is ResourceState.Error -> {
                            _authData.value = ResourceState.Error(it.exception)
                        }
                        else -> {}
                    }
                }
        }
    }

    fun checkSession() = getSessionUseCase.execute().asLiveData()

    fun clearDataStore(){
        viewModelScope.launch {
            clearSessionUseCase.invoke()
        }
    }
}