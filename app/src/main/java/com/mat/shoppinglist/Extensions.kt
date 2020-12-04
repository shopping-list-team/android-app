package com.mat.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun ViewModel.launch(func: suspend () -> Unit) {
    viewModelScope.launch {
        func()
    }
}