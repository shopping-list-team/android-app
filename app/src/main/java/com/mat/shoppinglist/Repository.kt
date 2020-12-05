package com.mat.shoppinglist

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class Repository(
    private val client: Webservice
) {
    suspend fun getList(accessCode: String): Result<ProductList> {
        return try {
            Result.Success(client.getList(accessCode))
        } catch(e: HttpException) {
            Result.Error(e)
        }
    }

    suspend fun getAllProductsForList(accessCode: String): Result<List<Product>> {
        return try {
            Result.Success(client.getAllProductsForList(accessCode))
        } catch(e: HttpException) {
            Result.Error(e)
        }
    }

    suspend fun postNewList(listName: String): Result<ProductList> {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", listName)
            .build()
        return try {
            Result.Success(client.postNewList(requestBody))
        } catch(e: HttpException) {
            Result.Error(e)
        }
    }
}