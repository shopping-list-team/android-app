package com.mat.shoppinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mat.shoppinglist.*
import com.mat.shoppinglist.data.Product
import com.mat.shoppinglist.data.ProductList
import com.mat.shoppinglist.model.Repository
import com.mat.shoppinglist.model.Result

class ListViewModel(
    private val repository: Repository
) : ViewModel() {

    private var _productList = MutableLiveData<Result<List<Product>>>()
    val productList: LiveData<Result<List<Product>>> get() = _productList

    private var _newProduct = MutableLiveData<Result<Product>>()
    val newProduct: LiveData<Result<Product>> get() = _newProduct

    private var _deletedProduct = MutableLiveData<Result<Unit>>()
    val deletedProduct: LiveData<Result<Unit>> get() = _deletedProduct

    private var _deletedList = MutableLiveData<Result<Unit>>()
    val deletedList: LiveData<Result<Unit>> get() = _deletedList

    private var _updatedList = MutableLiveData<Result<ProductList>>()
    val updatedList: LiveData<Result<ProductList>> get() = _updatedList

    private var _updatedProduct = MutableLiveData<Result<Product>>()
    val updatedProduct: LiveData<Result<Product>> get() = _updatedProduct

    fun loadProducts(accessCode: String) =
        launch {
            _productList.value = repository.getAllProductsForList(accessCode)
        }

    fun sendNewProduct(
            name: String,
            content: Int,
            accessCode: String,
            isBought: Boolean
    ) =
        launch {
            _newProduct.value = repository.postNewProduct(name, content, accessCode, isBought)
        }

    fun updateList(list: ProductList) =
        launch {
            _updatedList.value = repository.updateList(list)
        }

    fun deleteProduct(product: Product) =
        launch {
            _deletedProduct.value = repository.deleteProduct(product)
        }

    fun deleteList(list: ProductList) =
        launch {
            _deletedList.value = repository.deleteList(list)
        }

    fun updateProduct(product: Product) =
        launch {
            _updatedProduct.value = repository.updateProduct(product)
        }

}