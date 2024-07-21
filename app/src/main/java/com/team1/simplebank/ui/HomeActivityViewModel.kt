package com.team1.simplebank.ui

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.synrgy.xdomain.model.AccountModel
import com.synrgy.xdomain.model.Menu
import com.synrgy.xdomain.useCase.auth.GetSessionUseCase
import com.synrgy.xdomain.useCase.user.GetUserAccountUseCase
import com.team1.simplebank.R
import com.team1.simplebank.common.handler.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    private val getSessionUseCase: GetSessionUseCase,
    private val getUserAccountUseCase: GetUserAccountUseCase,
) : ViewModel() {
    private val _listDataItem = MutableLiveData<List<Menu>>()
    val listData: LiveData<List<Menu>> = _listDataItem

    private val _userAccount =
        MutableStateFlow<ResourceState<List<AccountModel>>>(ResourceState.Idle)
    val userAccount: StateFlow<ResourceState<List<AccountModel>>> = _userAccount

    private val _isShowMoreOrLessVisible: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isShowMoreOrLessVisible: LiveData<Boolean> = _isShowMoreOrLessVisible

    private val _isShowOrHideBalanceValue: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val isShowOrHideBalanceValue: LiveData<Boolean> = _isShowOrHideBalanceValue

    fun toggleShowMoreOrLessInformation(input: Boolean) {
        _isShowMoreOrLessVisible.value = input
    }

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


    private fun listItemMenu(resources: Resources): List<Menu> {
        return listOf(
            Menu(
                1,
                R.drawable.icon_tarik_tunai,
                resources.getString(R.string.cash_withdrawal_title)
            ),
            Menu(2, R.drawable.icon_deposit2, resources.getString(R.string.cash_deposit_title)),
            Menu(3, R.drawable.icon_transfer, resources.getString(R.string.transfer_title)),
            Menu(
                4,
                R.drawable.icon_virtual_account,
                resources.getString(R.string.virtual_account_title)
            ),
            Menu(5, R.drawable.icon_tarik_tunai, resources.getString(R.string.gold_tube_title)),
            Menu(6, R.drawable.icon_pay, resources.getString(R.string.pay_title)),
            Menu(
                7,
                R.drawable.icon_top_up_e_wallet,
                resources.getString(R.string.top_up_e_wallet_title)
            ),
            Menu(8, R.drawable.icon_etc, resources.getString(R.string.etc_title)),
        )
    }

    fun addDataRecyclerView(resources: Resources) {
        _listDataItem.value = listItemMenu(resources)
    }

    fun getSession() = getSessionUseCase.execute().asLiveData()


}