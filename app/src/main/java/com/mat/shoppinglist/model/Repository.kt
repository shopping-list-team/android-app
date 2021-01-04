package com.mat.shoppinglist.model

import com.mat.shoppinglist.data.Product
import com.mat.shoppinglist.data.ProductList
import okhttp3.MultipartBody
import retrofit2.HttpException

class Repository(
    private val client: Webservice
) {
    suspend fun getList(accessCode: String): Result<ProductList> =
        handleRequest {
            it.getList(accessCode)
        }

    suspend fun getAllProductsForList(accessCode: String): Result<List<Product>> =
            handleRequest {
                it.getAllProductsForList(accessCode).sortedBy { it.is_bought }
            }

    suspend fun postNewList(listName: String): Result<ProductList> {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", listName)
            .build()
        return handleRequest {
            it.postNewList(requestBody)
        }
    }

    suspend fun postNewProduct(
            name: String,
            content: Int,
            accessCode: String,
            isBought: Boolean
    ): Result<Product> {
        val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", name)
                .addFormDataPart("content", content.toString())
                .addFormDataPart("shopping_list", accessCode)
                .addFormDataPart("is_bought", isBought.toString())
                .build()
        return handleRequest {
            it.postNewProduct(requestBody)
        }
    }

    suspend fun updateProduct(product: Product): Result<Product> {
        val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", product.name)
                .addFormDataPart("content", product.content)
                .addFormDataPart("shopping_list", product.shopping_list)
                .addFormDataPart("is_bought", product.is_bought.toString())
                .build()
        return handleRequest {
            it.updateProduct(product.id, requestBody)
        }
    }

    suspend fun updateList(list: ProductList): Result<ProductList> {
        val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", list.name)
                .addFormDataPart("access_code", list.access_code)
                .build()
        return handleRequest {
            it.updateList(list.access_code, requestBody)
        }
    }

    suspend fun deleteProduct(product: Product): Result<Unit> =
            handleRequest {
                it.deleteProduct(product.id)
            }

    suspend fun deleteList(list: ProductList): Result<Unit> =
            handleRequest {
                it.deleteList(list.access_code)
            }

    private suspend fun<T> handleRequest(
            func: suspend (client: Webservice) -> T
    ) : Result<T> {
        return try {
            Result.Success(func(client))
        } catch(e: HttpException) {
            Result.Error(e)
        }
    }
}