package  com.plugin.monitor.net

import android.content.Context
import com.plugin.monitor.db.helper.DataFactory
import com.plugin.monitor.util.Utils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by cool on 2018/3/15.
 */
internal class NetInterceptor(var context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("data_head", Utils.getGson().toJson(DataFactory.createHeader(context)))
                .build()
        return chain.proceed(request)
    }
}