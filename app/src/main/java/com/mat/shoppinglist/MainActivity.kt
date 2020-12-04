package com.mat.shoppinglist

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object {
        var networkAvailable: Boolean = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }
}

val networkCallback = object: ConnectivityManager.NetworkCallback() {
    override fun onAvailable(network: Network) {
        MainActivity.networkAvailable = true
        Log.i("Network", "changed state to up")
    }

    override fun onLost(network: Network) {
        MainActivity.networkAvailable = false
        Log.i("Network", "changed state to down")
    }
}