package com.example.modulea.util

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }
}
