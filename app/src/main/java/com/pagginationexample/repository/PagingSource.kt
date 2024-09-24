package com.pagginationexample.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pagginationexample.network.ApiService
import com.pagginationexample.ui.model.Product
import javax.inject.Inject

class PagingSource @Inject constructor(private val apiService: ApiService) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val pageNumber = params.key ?: 1
            val response = apiService.getProducts(pageNumber,params.loadSize)

            LoadResult.Page(
                data = response.products,
                prevKey = null,
                nextKey = if (response.products.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}