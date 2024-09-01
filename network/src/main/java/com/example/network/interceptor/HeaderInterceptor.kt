package com.example.network.interceptor

import com.example.network.config.Config
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        requestBuilder.addHeader("Accept", "application/vnd.github+json")
        requestBuilder.addHeader("X-GitHub-Api-Version", "2022-11-28")
        Config.getAuth()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}