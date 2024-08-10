package com.team1.simplebank.ui.account_mutation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synrgy.xdomain.model.AccountModel
import com.synrgy.xdomain.model.FilterInput
import com.synrgy.xdomain.model.FilterStates
import com.synrgy.xdomain.model.MutationDataUI
import com.synrgy.xdomain.useCase.mutation.MutationUseCase
import com.synrgy.xdomain.useCase.user.GetUserAccountUseCase
import com.team1.simplebank.common.handler.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountMutationViewModel @Inject constructor(
    private val useCase: MutationUseCase,
    private val getUserAccountUseCase: GetUserAccountUseCase,
) : ViewModel() {

    // data for combine
    /*private val noAccount : MutableStateFlow<String> = MutableStateFlow("")
    private val month : MutableStateFlow<Int> = MutableStateFlow(0)
    private val type : MutableStateFlow<String?> = MutableStateFlow(null)*/

    //using data class for filtering
    // data di dalam filterstates bisa diupdate ya kedepannya untuk menggunakan data store aja
    private val _stateFiltering = MutableStateFlow<FilterStates>(FilterStates("",8,null))
    private val stateFiltering: StateFlow<FilterStates> = _stateFiltering.asStateFlow()

    //
    private val _dataMutationUI = MutableStateFlow<List<MutationDataUI>>(emptyList())
    val dataMutationUI: StateFlow<List<MutationDataUI>> = _dataMutationUI.asStateFlow()


    private val _noAccount:Flow<String?> = useCase.getNoAccount()
    val noAccount = _noAccount


    fun inputFiltering(filterInput: FilterInput){
        _stateFiltering.update {currentFilter->
            when(filterInput){
                is FilterInput.Month -> {currentFilter.copy(month = filterInput.month)}
                is FilterInput.NoAccount -> {currentFilter.copy(noAccount = filterInput.noAccount)}
                is FilterInput.Type -> {currentFilter.copy(type = filterInput.type)}
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun dataMutationOnUIWithFiltering():Flow<PagingData<MutationDataUI>>{
         return stateFiltering.flatMapLatest { valueFiltering->
             useCase.getDataMutation(
                 inputDataNoAccount = valueFiltering.noAccount,
                 inputDataMonth = valueFiltering.month,
                 inputType = valueFiltering.type
             )
         }.cachedIn(viewModelScope)
    }



    fun dataMutationUI(
        inputDataNoAccount: String,
        inputDataMont: Int,
        inputType: String? = null
    ): Flow<PagingData<MutationDataUI>> {
        return useCase.getDataMutation(inputDataNoAccount, inputDataMont, inputType)
            .cachedIn(viewModelScope)
    }


    val userAccountsData: LiveData<ResourceState<List<AccountModel>>> = liveData {
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