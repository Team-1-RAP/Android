package com.team1.simplebank.data.repositoryImpl.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.team1.simplebank.data.remote.api.ApiService
import com.team1.simplebank.data.remote.response.ItemPagingData

class MutationPagingSource(
    val apiService: ApiService,
    val inputDataNoAccount: String,
    val inputDataMonth: Int,
    val inputType: String
) : PagingSource<Int, ItemPagingData>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ItemPagingData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    //load data berdasarkan posisi
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemPagingData> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMutations(
                noAccount = inputDataNoAccount,
                page = position,
                month = inputDataMonth,
                size = params.loadSize
            )

            LoadResult.Page(
                data = responseData.data.pagingData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else -1,
                nextKey = if (responseData.data.pagingData.isNullOrEmpty()) null else +1,
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

}