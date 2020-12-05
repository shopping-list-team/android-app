package com.mat.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccessViewModel(
    private val repository: Repository
) : ViewModel() {

    private var _list  = MutableLiveData<Result<ProductList>>()
    val list: LiveData<Result<ProductList>> get() = _list
    private var _newList = MutableLiveData<Result<ProductList>>()
    val newList: LiveData<Result<ProductList>> get() = _newList

    fun loadList(accessCode: String) =
        launch {
            _list.value = repository.getList(accessCode)
        }

    fun addNewList(listName: String) =
        launch {
            _newList.value = repository.postNewList(listName)
        }
}