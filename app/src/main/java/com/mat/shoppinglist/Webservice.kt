package com.mat.shoppinglist

import okhttp3.RequestBody
import retrofit2.http.*

interface Webservice {
    @GET("shopping-lists/{access_code}")
    suspend fun getList(@Path(value = "access_code") accessCode: String) : ProductList

    @GET("items/")
    suspend fun getAllProductsForList(@Query("access_code") accessCode: String) : List<Product>

    @POST("shopping-lists/")
    suspend fun postNewList(@Body body: RequestBody) : ProductList

}