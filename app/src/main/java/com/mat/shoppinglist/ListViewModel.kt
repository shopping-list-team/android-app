package com.mat.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Response

class ListViewModel(
    private val repository: Repository
) : ViewModel() {

    private var _productList = MutableLiveData<Result<List<Product>>>()
    val productList get() = _productList

    fun loadProducts(accessCode: String) =
        launch {
            _productList.value = repository.getAllProductsForList(accessCode)
        }

}