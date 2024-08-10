package com.team1.simplebank.ui

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.synrgy.xdomain.model.AccountModel
import com.synrgy.xdomain.model.GetAmountsMutationUI
import com.synrgy.xdomain.model.Menu
import com.synrgy.xdomain.useCase.user.GetUserAccountUseCase
import com.team1.simplebank.R
import com.team1.simplebank.common.handler.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    private val getUserAccountUseCase: GetUserAccountUseCase,
) : ViewModel() {
    private val _listDataItem = MutableLiveData<List<Menu>>()
    val listData: LiveData<List<Menu>> = _listDataItem

    private val _userAccount = MutableStateFlow<ResourceState<List<AccountModel>>>(ResourceState.Idle)
    val userAccount: StateFlow<ResourceState<List<AccountModel>>> = _userAccount

    private val _noAccount : Flow<String?> = getUserAccountUseCase.getNoAccount()
    val noAccount = _noAccount

    private val _isShowOrHideBalanceValue: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isShowOrHideBalanceValue: LiveData<Boolean> = _isShowOrHideBalanceValue

    private val _amounts:MutableStateFlow<ResourceState<GetAmountsMutationUI>> = MutableStateFlow(ResourceState.Idle)
    val amounts:StateFlow<ResourceState<GetAmountsMutationUI>> = _amounts.asStateFlow()

    fun toggleShowOrHideBalance(input: Boolean) {
        _isShowOrHideBalanceValue.value = input
    }

    private fun getUserAccountsDetails() {
        viewModelScope.launch {
            getUserAccountUseCase.invoke().collect {
                when (it) {
                    is ResourceState.Loading -> {
                        _userAccount.value = ResourceState.Loading
                    }

                    is ResourceState.Success -> {
                        _userAccount.value = ResourceState.Success(it.data)
                    }

                    is ResourceState.Error -> {
                        _userAccount.value = ResourceState.Error(it.exception)
                    }

                    else -> {}
                }
            }
        }
    }

    val userAccountsDetails: LiveData<ResourceState<List<AccountModel>>> = liveData {
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

    fun getAmounts(noAccount:String){
        viewModelScope.launch {
            getUserAccountUseCase.getAmounts(noAccount).collect{
                when(it){
                    is ResourceState.Loading -> {
                        _amounts.value = ResourceState.Loading
                    }
                    is ResourceState.Success -> {
                        _amounts.value = ResourceState.Success(it.data)
                    }
                    is ResourceState.Error -> {
                        _amounts.value = ResourceState.Error(it.exception)
                    }
                    is ResourceState.Idle -> {}
                }
            }
        }
    }

    fun saveNoAccount(noAccount:String){
        viewModelScope.launch {
            getUserAccountUseCase.saveNoAccount(noAccount)
        }
        Log.d("SaveNoAccount", "saveNoAccount: triggered")
    }

    private fun listItemMenu(resources: Resources): List<Menu> {
        return listOf(
            Menu(1, R.drawable.state_cash_withdrawal, resources.getString(R.string.cash_withdrawal_title)),
            Menu(2, R.drawable.state_setor_tunai, resources.getString(R.string.cash_deposit_title)),
            Menu(3, R.drawable.state_transfer, resources.getString(R.string.transfer_title)),
            Menu(4, R.drawable.state_virtual_account, resources.getString(R.string.virtual_account_title)),
            Menu(5, R.drawable.state_cash_withdrawal, resources.getString(R.string.gold_tube_title)),
            Menu(6, R.drawable.state_bayar, resources.getString(R.string.pay_title)),
            Menu(7, R.drawable.state_top_up,resources.getString(R.string.top_up_e_wallet_title)),
            Menu(8, R.drawable.state_lain_lain, resources.getString(R.string.etc_title)),
        )
    }


    fun addDataRecyclerView(resources: Resources) {
        _listDataItem.value = listItemMenu(resources)
    }



}