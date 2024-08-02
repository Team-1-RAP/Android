package com.team1.simplebank.ui.account_mutation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.synrgy.xdomain.model.MutationDataUI
import com.synrgy.xdomain.useCase.mutation.MutationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountMutationViewModel @Inject constructor
    (private val useCase: MutationUseCase) : ViewModel() {
    private val _dataMutationUI = MutableStateFlow<List<MutationDataUI>>(emptyList())
    val dataMutationUI: StateFlow<List<MutationDataUI>> = _dataMutationUI.asStateFlow()
    fun dataMutationUI(
        inputDataNoAccount: String,
        inputDataMont: Int,
        inputType: String? = null
    ): Flow<PagingData<MutationDataUI>> {
        return useCase.getDataMutation(inputDataNoAccount, inputDataMont, inputType)
            .cachedIn(viewModelScope)
    }

    fun getDataWithoutPagination(
        inputDataNoAccount: String,
        inputDataMont: Int,
        inputType: String? = null
    ) {
        viewModelScope.launch {
            useCase.getDataWithoutPagination(inputDataNoAccount, inputDataMont, inputType).collect{ it ->
                _dataMutationUI.value = it
            }
        }
    }
}