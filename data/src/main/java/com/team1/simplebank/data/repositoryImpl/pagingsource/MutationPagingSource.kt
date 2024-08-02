package com.team1.simplebank.data.repositoryImpl.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synrgy.xdomain.model.MutationDataUI
import com.team1.simplebank.data.mapper.mapperMutationResponseApiToMutationDataUI
import com.team1.simplebank.data.remote.api.ApiService
import com.team1.simplebank.data.remote.response.ItemPagingData
import com.team1.simplebank.data.remote.response.MutationResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class MutationPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val inputDataNoAccount: String,
    private val inputDataMonth: Int,
    private val inputType:String?=null,
) : PagingSource<Int, MutationDataUI>() {

    companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    /*init{
        suspend fun responseData():MutationResponse {
            val service= apiService.getMutations(
                noAccount = inputDataNoAccount,
                page = 1,
                month = inputDataMonth,
                size = 5,
                type = inputType,
            )
            Log.d("init mutation paging source", "responseData: $service")
            return service
        }

        runBlocking {
            responseData()
        }
    }*/




    //load data berdasarkan posisi
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MutationDataUI> {
        return try {
            Log.d("MutationSource", "Creating PagingSource with noAccount: $inputDataNoAccount, month: $inputDataMonth, type: $inputType")

            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMutations(
                noAccount = inputDataNoAccount,
                page = position,
                month = inputDataMonth,
                size = params.loadSize,
                type = inputType,
            )

            val data = responseData.data.pagingData

            Log.d("Response", "getUserAccount: $responseData")

            val result = mapperMutationResponseApiToMutationDataUI(data)

            LoadResult.Page(
                data = result,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else -1,
                nextKey = if (responseData.data.pagingData.isEmpty()) null else +1,
            )

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MutationDataUI>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}