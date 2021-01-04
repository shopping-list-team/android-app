package com.mat.shoppinglist.data

data class Product(
    val id: Int,
    val name: String,
    val content: String,
    val shopping_list: String,
    val is_bought: Boolean
)