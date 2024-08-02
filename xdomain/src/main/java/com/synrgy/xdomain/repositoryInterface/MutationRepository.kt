package com.synrgy.xdomain.repositoryInterface

import androidx.paging.PagingData
import com.synrgy.xdomain.model.MutationDataUI
import kotlinx.coroutines.flow.Flow

interface MutationRepository {
    fun getDataMutation(
        inputDataNoAccount: String,
        inputDataMonth: Int,
        inputType: String? = null,
    ): Flow<PagingData<MutationDataUI>>

    fun getDataWithoutPagination(
        inputDataNoAccount: String,
        inputDataMonth: Int,
        inputType: String? = null,
    ): Flow<List<MutationDataUI>>
}