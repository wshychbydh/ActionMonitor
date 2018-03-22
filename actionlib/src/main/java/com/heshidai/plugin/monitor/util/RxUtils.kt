package com.heshidai.plugin.monitor.util


import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by cool on 18-3-16.
 */
object RxUtils {
    private const val MAX_COUNT = 5
    private var RX_SCHEDULER = Schedulers.from(Executors.newFixedThreadPool(MAX_COUNT,
            object : ThreadFactory {
                private val count = AtomicInteger()
                override fun newThread(r: Runnable): Thread {
                    val c = count.incrementAndGet()
                    return Thread(r, "RxThread-$c")
                }
            }))
    private var RX_SINGLE = Schedulers.from(Executors.newSingleThreadExecutor())

    fun <T> applySingleScheduler(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(RX_SINGLE)
                    .observeOn(RX_SINGLE)
        }
    }

    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(RX_SCHEDULER)
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
