package com.heshidai.plugin.monitor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.heshidai.plugin.monitor.db.helper.SqliteHelper

@SuppressLint("StaticFieldLeak")
/**
 * Created by cool on 2018/2/28.
 */

object MonitorSdk {

    var context: Context? = null
    var isSdkInited = false

    /**
     * You have to call first. Suggest in application's onCreate
     */
    @JvmStatic
    fun init(context: Context) {
        MonitorSdk.context = context.applicationContext
        SqliteHelper.get()
        isSdkInited = true
        context.startService(Intent(context, SyncService::class.java))
    }

    @JvmStatic
    fun release() {
        context = null
        isSdkInited = false
    }
}
