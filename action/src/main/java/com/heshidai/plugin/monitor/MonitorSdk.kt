package com.heshidai.plugin.monitor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.heshidai.plugin.monitor.db.helper.DataHelper
import com.heshidai.plugin.monitor.db.helper.SqliteHelper
import com.heshidai.plugin.monitor.location.LocationHelper

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
    }

    /**
     * 登陆后调用，保存用户信息
     */
    @JvmStatic
    fun saveUserInfo(userInfo: String) {
        if (context == null) {
            throw IllegalStateException("You should call MonitorSdk.init() first !")
        }
        DataHelper.saveUserInfo(context!!, userInfo)
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
