package com.example.productlokal

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.productlokal.adapter.MyAdapter
import com.example.productlokal.viewModel.MainActivityViewModel
import com.google.android.material.progressindicator.LinearProgressIndicator
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private val refreshHandler = Handler(Looper.getMainLooper())
    private lateinit var progressLine : LinearProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh = findViewById(R.id.swipeRefresh)
        progressLine = findViewById(R.id.progressLine)

        initRecyclerView()

        if(isNetworkAvailable()){
            initViewModel()
        } else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }

        swipeRefresh.setOnRefreshListener {
            if (isNetworkAvailable()) {
                initViewModel()
            } else {
                swipeRefresh.isRefreshing = false
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
        }

        scheduleAutoRefresh()

    }


    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        myAdapter = MyAdapter(this)
        recyclerView.adapter = myAdapter
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun initViewModel() {
        if(isNetworkAvailable()){
            progressLine.visibility = View.VISIBLE
            val viewModel : MainActivityViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
            viewModel.getLiveDataObserver().observe(this) {
                //lambda function
                if (it != null) {
                    myAdapter.setData(it)
                    myAdapter.notifyDataSetChanged()
                    val currentTime = getCurrentTime()
                    findViewById<TextView>(R.id.last_update).text = "Last refreshed: $currentTime"
                    progressLine.visibility = View.GONE
                } else {
                    progressLine.visibility = View.GONE
                    Toast.makeText(this, "Error in getting data", Toast.LENGTH_SHORT).show()
                }
            }
            if(swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false
            viewModel.makeApiCall()
        }
        else{
            progressLine.visibility = View.GONE
            if(swipeRefresh.isRefreshing) swipeRefresh.isRefreshing = false
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return (networkInfo != null) && networkInfo.isConnected
    }

    private fun scheduleAutoRefresh() {
        val delay = 3*60*1000L
        refreshHandler.postDelayed(object: Runnable{
            override fun run(){
                Log.d("Checking", "Refresh")
                if(isNetworkAvailable()){
                    initViewModel()
                }
                refreshHandler.postDelayed(this, delay)
            }
        }, delay)
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    override fun onDestroy() {
        refreshHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }






}