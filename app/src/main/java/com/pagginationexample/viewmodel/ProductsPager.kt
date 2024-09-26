package com.pagginationexample.viewmodel

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pagginationexample.network.ApiService
import com.pagginationexample.repository.PagingSource
import com.pagginationexample.ui.model.Product
import com.pagginationexample.ui.model.ProductsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.http.Query
import javax.inject.Inject

class ProductsPager @Inject constructor(private val apiService: ApiService) {

    fun getPagingData(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { PagingSource(apiService) }
        ).flow
    }


    fun getItemsOfProduct(skip: Int, limit: Int): Flow<ProductsResponse> = flow {
        val response = apiService.getProducts(skip, limit)
        emit(response)
    }.catch { e ->
        Log.e("dataerror", "getStatus: " + e.message)
    }.flowOn(Dispatchers.IO)
}