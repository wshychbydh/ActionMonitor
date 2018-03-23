package  com.heshidai.plugin.monitor.net

import com.google.gson.GsonBuilder
import com.heshidai.plugin.monitor.MonitorSdk
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by cool on 2018/3/8.
 */
internal object Api {

    private const val BASE_URL = "http://collect.heshidai.com/"
    var service: MonitorService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        service = retrofit.create(MonitorService::class.java)
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
                .connectTimeout(20 * 1000, TimeUnit.SECONDS)
                .addInterceptor(LoggerInterceptor())
                .addNetworkInterceptor(NetInterceptor(MonitorSdk.context!!))
                .build()
    }
}

fun <T> Observable<T>.doSubscribe(observer: Observer<T>) {
    subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(observer)
}