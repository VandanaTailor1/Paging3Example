package com.pagginationexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pagginationexample.ui.model.Product
import com.pagginationexample.ui.model.ProductsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productsPager: ProductsPager)
    : ViewModel() {

    val pagingDataFlow :  Flow<PagingData<Product>> =
        productsPager.getPagingData()
        .cachedIn(viewModelScope)

    private val _products =
        MutableStateFlow<ProductsResponse?>(null)
    val products: StateFlow<ProductsResponse?> = _products

    fun getProductsItem(skip : Int ,limit : Int) {
        viewModelScope.launch {
            productsPager.getItemsOfProduct(skip,limit).collect {
                _products.value = it
            }
        }
    }
}