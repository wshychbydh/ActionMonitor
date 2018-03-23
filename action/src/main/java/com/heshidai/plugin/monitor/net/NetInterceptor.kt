package  com.heshidai.plugin.monitor.net

import android.content.Context
import com.google.gson.Gson
import com.heshidai.plugin.monitor.db.helper.DataFactory
import com.heshidai.plugin.monitor.util.LogUtils
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by cool on 2018/3/15.
 */
internal class NetInterceptor(var context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("data_head", Gson().toJson(DataFactory.createHeader(context)))
                .build()
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