package com.team1.simplebank.data.repositoryImpl.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.synrgy.xdomain.model.MutationDataUI
import com.team1.simplebank.data.mapper.mapperMutationResponseApiToMutationDataUI
import com.team1.simplebank.data.remote.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MutationPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val inputDataNoAccount: String,
    private val inputDataMonth: Int,
    private val inputType:String?=null,
) : PagingSource<Int, MutationDataUI>() {

    companion object {
        const val INITIAL_PAGE_INDEX = 0
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

            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getMutations(
                noAccount = inputDataNoAccount,
                page = position,
                month = inputDataMonth,
                size = 10,
                type = inputType,
            )

            val data = responseData.data.pagingData
            val sortedData = data.sortedBy { it.date }
            val parsedData = withContext(Dispatchers.Default){
                mapperMutationResponseApiToMutationDataUI(data)
            }
            val result = mapperMutationResponseApiToMutationDataUI(data)
            Log.d("MutationSource", "Result: $result")

            LoadResult.Page(
                data = result,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (data.isEmpty()) null else position+1,
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