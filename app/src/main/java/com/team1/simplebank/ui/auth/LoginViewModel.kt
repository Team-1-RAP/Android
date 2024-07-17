package com.team1.simplebank.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.synrgy.xdomain.model.AuthModel
import com.synrgy.xdomain.useCase.auth.GetSessionUseCase
import com.synrgy.xdomain.useCase.auth.LoginUseCase
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel (
    private val loginUseCase: LoginUseCase,
    private val getSessionUseCase: GetSessionUseCase,
) : ViewModel() {

    private val _authData = MutableStateFlow<ResourceState<AuthModel>>(ResourceState.Loading)
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
                    }
                }
        }
    }

    fun checkSession() = getSessionUseCase.execute().asLiveData()
}