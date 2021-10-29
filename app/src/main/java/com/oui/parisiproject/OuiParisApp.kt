package com.oui.parisiproject

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import android.net.NetworkInfo

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.internal.Internal.instance


@HiltAndroidApp
class OuiParisApp: Application() {

    companion object {
        var instance: OuiParisApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
    }

    fun getInstance(): OuiParisApp? {
        return instance
    }


    fun hasNetwork(): Boolean {
        return instance!!.isNetworkAvailable()
    }

    private fun isNetworkAvailable() =
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }

}