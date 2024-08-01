package com.team1.simplebank.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.team1.simplebank.data.remote.api.ApiService
import com.team1.simplebank.data.remote.response.ItemPagingData
import com.team1.simplebank.data.repositoryImpl.pagingsource.MutationPagingSource
import kotlinx.coroutines.flow.Flow

class MutationsRepositoryImpl(
    val apiService: ApiService,
    val inputDataNoAccount: String,
    val inputDataMonth: Int,
    val inputType: String
) {
    fun getDataMutation(): Flow<PagingData<ItemPagingData>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                MutationPagingSource(apiService,inputDataNoAccount,inputDataMonth,inputType)
            }
        ).flow
    }
}