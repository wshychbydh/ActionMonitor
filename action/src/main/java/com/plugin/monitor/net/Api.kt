package  com.plugin.monitor.net

import com.plugin.monitor.MonitorSdk
import com.plugin.monitor.util.Utils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by cool on 2018/3/8.
 */
internal object Api {

    var url = "http://collect.heshidai.com/api/collect/info"
    var service: MonitorService

    init {
        url = Utils.getAppMetaDataByKey(MonitorSdk.context!!, "MONITOR_URL") ?: url
        val retrofit = Retrofit.Builder()
                .baseUrl("https://empty")
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create(Utils.getGson()))
                .build()
        service = retrofit.create(MonitorService::class.java)
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
                .connectTimeout(20 * 1000, TimeUnit.SECONDS)
                .addNetworkInterceptor(NetInterceptor(MonitorSdk.context!!))
                .addNetworkInterceptor(LogInterceptor())
                .build()
    }
}