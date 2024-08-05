package com.synrgy.xdomain.repositoryInterface

import androidx.paging.PagingData
import com.synrgy.xdomain.model.GetAmountsMutationUI
import com.synrgy.xdomain.model.MutationDataUI
import com.team1.simplebank.common.handler.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MutationRepository {
    fun getDataMutation(
        inputDataNoAccount: String,
        inputDataMonth: Int,
        inputType: String? = null,
    ): Flow<PagingData<MutationDataUI>>

    fun getNoAccount():Flow<String?>

}