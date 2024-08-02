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
import javax.inject.Inject

@HiltViewModel
class AccountMutationViewModel @Inject constructor
    (private val useCase: MutationUseCase) : ViewModel() {

    fun dataMutationUI(
        inputDataNoAccount: String,
        inputDataMont: Int,
        inputType: String? = null
    ): Flow<PagingData<MutationDataUI>> {
        return useCase.getDataMutation(inputDataNoAccount, inputDataMont, inputType)
            .cachedIn(viewModelScope)
    }
}