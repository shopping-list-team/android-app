package com.mat.shoppinglist.model

import com.mat.shoppinglist.data.Product
import com.mat.shoppinglist.data.ProductList
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Webservice {

    @GET("shopping-lists/{access_code}")
    suspend fun getList(@Path(value = "access_code") accessCode: String) : ProductList

    @GET("items/")
    suspend fun getAllProductsForList(@Query("access_code") accessCode: String) : List<Product>

    @POST("shopping-lists/")
    suspend fun postNewList(@Body body: RequestBody) : ProductList

    @POST("items/")
    suspend fun postNewProduct(@Body body: RequestBody) : Product

    @PUT("all-items/{id}/")
    suspend fun updateProduct(
            @Path(value = "id") id: Int,
            @Body body: RequestBody
    ) : Product

    @PUT("shopping-lists/{access_code}/")
    suspend fun updateList(
            @Path(value = "access_code") accessCode: String,
            @Body body: RequestBody
    ) : ProductList

    @DELETE("shopping-lists/{access_code}/")
    suspend fun deleteList(@Path(value = "access_code") accessCode: String) : Response<Unit>

    @DELETE("all-items/{id}/")
    suspend fun deleteProduct(@Path(value = "id") id: Int) : Response<Unit>

}