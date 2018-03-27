package com.heshidai.plugin.monitor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.heshidai.plugin.monitor.db.helper.DataHelper
import com.heshidai.plugin.monitor.db.helper.SqliteHelper
import com.heshidai.plugin.monitor.location.LocationHelper
import com.heshidai.plugin.monitor.location.NetworkHelper

@SuppressLint("StaticFieldLeak")
/**
 * Created by cool on 2018/2/28.
 */

object MonitorSdk {

    internal var context: Context? = null
    internal var isSdkInited = false

    /**
     * You have to call first. Suggest in application's onCreate
     */
    @JvmStatic
    fun init(context: Context) {
        MonitorSdk.context = context.applicationContext
        SqliteHelper.get()
        isSdkInited = true
        context.startService(Intent(context, SyncService::class.java))
        LocationHelper.startLocation(context)
        NetworkHelper.requestNetworkInfo(context)
    }

    /**
     * 在适当的时候调用，如登陆成功
     */
    @JvmStatic
    fun savePhone(phone: String) {
        if (context == null) {
            throw IllegalStateException("You should call MonitorSdk.init() first !")
        }
        DataHelper.savePhone(context!!, phone)
    }

    /**
     * You may never call this
     */
    @JvmStatic
    fun release() {
        context = null
        isSdkInited = false
    }
}
