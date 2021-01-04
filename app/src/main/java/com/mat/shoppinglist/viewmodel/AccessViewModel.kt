package com.mat.shoppinglist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mat.shoppinglist.data.ProductList
import com.mat.shoppinglist.model.Repository
import com.mat.shoppinglist.model.Result
import com.mat.shoppinglist.launch

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