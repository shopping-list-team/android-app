package com.mat.shoppinglist

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

fun ViewModel.launch(func: suspend () -> Unit) {
    viewModelScope.launch {
        func()
    }
}

fun Fragment.shortToast(text: String) {
    Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(text: String) {
    Toast.makeText(requireActivity(), text, Toast.LENGTH_LONG).show()
}