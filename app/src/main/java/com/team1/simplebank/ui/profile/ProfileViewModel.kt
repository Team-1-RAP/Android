package com.team1.simplebank.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.synrgy.xdomain.model.AccountModel
import com.synrgy.xdomain.useCase.auth.ClearSessionUseCase
import com.synrgy.xdomain.useCase.user.GetUserAccountUseCase
import com.team1.simplebank.common.handler.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val clearSessionUseCase: ClearSessionUseCase,
    private val getUserAccountUseCase: GetUserAccountUseCase,
): ViewModel() {
    private val _isShowMoreOrLessVisible: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isShowMoreOrLessVisible: LiveData<Boolean> = _isShowMoreOrLessVisible


    fun logout() {
        viewModelScope.launch {
            clearSessionUseCase.invoke()
        }
    }
    fun toggleShowMoreOrLessInformation(input: Boolean) {
        _isShowMoreOrLessVisible.value = input
    }
    val userAccountsDetailsInProfileFragment: LiveData<ResourceState<List<AccountModel>>> = liveData {
        getUserAccountUseCase.getUserAccounts().collect {
            when (it) {
                is ResourceState.Loading -> {
                    emit(ResourceState.Loading)
                }

                is ResourceState.Success -> {
                    emit(ResourceState.Success(it.data))
                }

                is ResourceState.Error -> {
                    emit(ResourceState.Error(it.exception))
                }

                else -> {}
            }
        }
    }



}