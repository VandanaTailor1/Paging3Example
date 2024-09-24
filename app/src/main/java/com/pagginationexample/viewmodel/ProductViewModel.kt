package com.pagginationexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pagginationexample.ui.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productsPager: ProductsPager)
    : ViewModel() {

    val pagingDataFlow :  Flow<PagingData<Product>> =
        productsPager.getPagingData()
        .cachedIn(viewModelScope)

}