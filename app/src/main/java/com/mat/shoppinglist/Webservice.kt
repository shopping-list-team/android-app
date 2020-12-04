package com.mat.shoppinglist

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Webservice {
    @GET("shopping-lists/{access_code}")
    suspend fun getList(@Path(value = "access_code") accessCode: String) : ProductList

    @GET("items/")
    suspend fun getAllProductsForList(@Query("access_code") accessCode: String) : List<Product>

}