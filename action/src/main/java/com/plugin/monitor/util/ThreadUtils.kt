package com.plugin.monitor.util

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
}