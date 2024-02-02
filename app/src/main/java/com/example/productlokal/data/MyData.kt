package com.example.productlokal.data

data class MyData(
    val limit: Int,
    val products: ArrayList<Product>,
    val skip: Int,
    val total: Int
)