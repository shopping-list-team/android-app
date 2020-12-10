package com.mat.shoppinglist

interface ProductHandler {
    fun removeProduct(index: Int)
    fun updateProduct(index: Int, bought: Boolean)
}