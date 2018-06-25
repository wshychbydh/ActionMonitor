package com.plugin.monitor.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

/**
 * Created by cool on 2018/3/26.
 */
internal object ThreadUtils {

    private val serviceExecutor = Executors.newSingleThreadExecutor()
    private val trackExecutor = Executors.newSingleThreadExecutor()

    fun executeOnService(R: () -> Unit) {
        serviceExecutor.execute(R)
    }

    fun executeOnTrack(R: () -> Unit) {
        trackExecutor.execute(R)
    }

    @JvmStatic
    fun <T> asyncTask(async: () -> T, ui: (data: T) -> Unit) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            trackExecutor.execute {
                val data = async.invoke()
                Handler(Looper.getMainLooper()).post {
                    ui.invoke(data)
                }
            }
        } else {
            val data = async.invoke()
            Handler(Looper.getMainLooper()).post {
                ui.invoke(data)
            }
        }
    }
}