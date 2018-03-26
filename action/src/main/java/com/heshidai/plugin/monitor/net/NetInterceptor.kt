package  com.heshidai.plugin.monitor.net

import android.content.Context
import com.google.gson.Gson
import com.heshidai.plugin.monitor.db.helper.DataFactory
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
        return chain.proceed(request)
    }
}