package com.plugin.monitor.net

import com.plugin.monitor.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by cool on 2018/3/8.
 */
internal class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        LogUtils.e("request", request.toString())
        LogUtils.e("header", request.headers().toString())
        val response = chain.proceed(request)
        LogUtils.e("response", response.toString())
        return response
    }
}