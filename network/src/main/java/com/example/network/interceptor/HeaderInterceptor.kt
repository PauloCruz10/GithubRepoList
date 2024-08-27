package com.example.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        requestBuilder.addHeader("Accept", "application/vnd.github+json")
        requestBuilder.addHeader("X-GitHub-Api-Version", "2022-11-28")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}