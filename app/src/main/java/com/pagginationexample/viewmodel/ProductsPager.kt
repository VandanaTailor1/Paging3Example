package com.pagginationexample.viewmodel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pagginationexample.network.ApiService
import com.pagginationexample.repository.PagingSource
import com.pagginationexample.ui.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsPager @Inject constructor(private val apiService: ApiService) {


    fun getPagingData(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
//                maxSize = 40,
//                initialLoadSize = 23
            ),
            pagingSourceFactory = { PagingSource(apiService) }
        ).flow
    }





}