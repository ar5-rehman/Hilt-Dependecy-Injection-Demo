package com.oui.parisiproject.common

import okhttp3.Interceptor
import okhttp3.Response
import com.oui.parisiproject.OuiParisApp
import okhttp3.Request


class OfflineInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        if (!OuiParisApp.instance?.hasNetwork()!!) {
            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
        return chain.proceed(request)
    }
}