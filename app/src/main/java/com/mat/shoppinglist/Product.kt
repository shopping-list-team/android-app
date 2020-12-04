package com.mat.shoppinglist

data class Product(
    val content: String,
    val id: Int,
    val is_bought: Boolean,
    val name: String,
    val shopping_list: String
)