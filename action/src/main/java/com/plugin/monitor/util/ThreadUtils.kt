package com.plugin.monitor.util

import java.util.concurrent.Executors

/**
 * Created by cool on 2018/3/26.
 */
internal object ThreadUtils {
    private val executors = Executors.newSingleThreadExecutor()
    fun execute(R: () -> Unit) {
        executors.execute(R)
    }
}