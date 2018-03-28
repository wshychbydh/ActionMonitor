package com.heshidai.plugin.monitor

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import com.heshidai.plugin.monitor.db.helper.DataHelper
import com.heshidai.plugin.monitor.db.helper.SqliteHelper
import com.heshidai.plugin.monitor.lifecycle.impl.ActivityLifecycleImpl
import com.heshidai.plugin.monitor.location.LocationHelper
import com.heshidai.plugin.monitor.location.NetworkHelper
import com.heshidai.plugin.monitor.util.LogUtils

@SuppressLint("StaticFieldLeak")
/**
 * Created by cool on 2018/2/28.
 */

object MonitorSdk {

    internal var context: Context? = null

    /**
     * You have to call in application's onCreate
     */
    @JvmStatic
    fun init(application: Application) {
        init(application, false)
    }

    /**
     * call in application's onCreate
     * If you want to print log , set 'debugAble' true,
     */
    @JvmStatic
    fun init(application: Application, debugAble: Boolean = false) {
        context = application.applicationContext
        LogUtils.setDebugAble(debugAble)
        SqliteHelper.get()
        application.registerActivityLifecycleCallbacks(ActivityLifecycleImpl())
        application.startService(Intent(context!!, SyncService::class.java))
        LocationHelper.startLocation(context!!)
        NetworkHelper.requestNetworkInfo(context!!)
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
}
