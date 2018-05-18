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
                .addHeader("data_head", encodeHeadInfo(Utils.getGson().toJson(DataFactory.createHeader(context))))
                .build()
        return chain.proceed(request)
    }

    //replace special char(include "\n" and chinese)
    private fun encodeHeadInfo(headInfo: String): String {
        val newValue = headInfo.replace("\n", "")
        val stringBuffer = StringBuffer()
        var i = 0
        val length = newValue.length
        while (i < length) {
            val c = newValue[i]
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", c.toInt()))
            } else {
                stringBuffer.append(c)
            }
            i++
        }
        return stringBuffer.toString()
    }
}