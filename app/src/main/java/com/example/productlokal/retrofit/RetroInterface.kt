package com.example.productlokal.retrofit

import com.example.productlokal.data.MyData
import retrofit2.Call
import retrofit2.http.GET

interface RetroInterface {
    @GET("products")
    fun getProductData() : Call<MyData>
}