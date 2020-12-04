package com.mat.shoppinglist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccessViewModel(
    private val repository: Repository
) : ViewModel() {

    private var _list  = MutableLiveData<Result<ProductList>>()
    val list: MutableLiveData<Result<ProductList>> get() = _list

    fun loadList(accessCode: String) =
        launch {
            _list.value = repository.getList(accessCode)
        }
}