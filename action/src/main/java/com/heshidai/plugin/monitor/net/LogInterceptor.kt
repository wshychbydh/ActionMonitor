package com.heshidai.plugin.monitor.net

import com.heshidai.plugin.monitor.util.LogUtils
import okhttp3.FormBody
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
        if (request.body() != null && request.body() is FormBody) {
            printBody(request.body() as FormBody)
        }
        val response = chain.proceed(request)
        LogUtils.e("response", response.toString())
        return response
    }

    private fun printBody(formBody: FormBody) {
        LogUtils.e("body", "--s-->")
        (0..formBody.size()).forEach {
            LogUtils.e("body", "${formBody.encodedName(it)} : ${formBody.encodedValue(it)}")
        }
        LogUtils.e("body", "--e-->")
    }
}