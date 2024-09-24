package com.pagginationexample.network

import com.pagginationexample.ui.model.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(
        @Query("skip") skip : Int,
        @Query("limit") limit: Int
    ) : ProductsResponse

}