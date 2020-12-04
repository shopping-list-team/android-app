package com.mat.shoppinglist

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
}