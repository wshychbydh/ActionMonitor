package com.plugin.monitor.util

import android.os.HandlerThread

/**
 *Created by cool on 2018/6/4
 */
internal class LooperThread(name: String, val R: () -> Unit) : HandlerThread(name) {
    override fun onLooperPrepared() {
        super.onLooperPrepared()
        R.invoke()
    }
}