package com.synrgy.xdomain.useCase.mutation

import android.util.Log
import androidx.paging.PagingData
import com.synrgy.xdomain.model.MutationDataUI
import com.synrgy.xdomain.repositoryInterface.MutationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MutationUseCase @Inject constructor(private val mutationRepository: MutationRepository) {
    fun getDataMutation(
        inputDataNoAccount: String,
        inputDataMonth: Int,
        inputType: String? = null
    ): Flow<PagingData<MutationDataUI>> {
        return mutationRepository.getDataMutation(
            inputDataNoAccount = inputDataNoAccount,
            inputDataMonth = inputDataMonth,
            inputType = inputType,
        )
    }

    fun getDataWithoutPagination(
        inputDataNoAccount: String,
        inputDataMont: Int,
        inputType: String? = null
    ): Flow<List<MutationDataUI>> {
        return mutationRepository.getDataWithoutPagination(inputDataNoAccount, inputDataMont, inputType)
    }
}