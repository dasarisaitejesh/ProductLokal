package com.example.productlokal.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        private const val base_url = "https://dummyjson.com/"

        fun getRetroInstance() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }

}