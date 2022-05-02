package com.example.covidtracker.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionManager {

    fun checkConnectivity(context: Context) : Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo : NetworkInfo? = connectivityManager.activeNetworkInfo

        if(networkInfo?.isConnected !=null)
        {
            return networkInfo.isConnected
        }
        return false

    }

}