package com.mat.shoppinglist.data

interface ProductHandler {
    fun removeProduct(index: Int)
    fun updateProduct(index: Int, bought: Boolean)
}