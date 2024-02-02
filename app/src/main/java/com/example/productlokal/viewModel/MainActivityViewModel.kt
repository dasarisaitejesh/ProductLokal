package com.example.productlokal.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productlokal.data.MyData
import com.example.productlokal.data.Product
import com.example.productlokal.retrofit.RetroInstance
import com.example.productlokal.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {

    var liveDataList: MutableLiveData<ArrayList<Product>> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<ArrayList<Product>>{
        return liveDataList
    }

    fun makeApiCall(){
        Log.d("Checking1", "Called Api")
        val retroService = RetroInstance.getRetroInstance().create(RetroInterface::class.java)
        val retroData = retroService.getProductData()
        retroData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                Log.d("Checking", "Got data from API")
                val data =response.body()
                if (data != null) {
                    liveDataList.postValue(data.products)
                }
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Log.d("checking2", "NotWork")
                liveDataList.postValue(null)
            }
        })
    }

}