package com.plugin.monitor

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import com.plugin.monitor.db.helper.DataHelper
import com.plugin.monitor.db.helper.SqliteHelper
import com.plugin.monitor.lifecycle.impl.ActivityLifecycleImpl
import com.plugin.monitor.location.LocationHelper
import com.plugin.monitor.location.NetworkHelper
import com.plugin.monitor.util.LogUtils
import com.plugin.monitor.util.ServiceUtil

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
        ServiceUtil.startSyncService(context!!, Intent(context, SyncService::class.java))
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

    /**
     * 多渠道打包时若不能在manifest中配置渠道，可主动调用该代码设置
     */
    @JvmStatic
    fun saveChannel(channel: String) {
        if (context == null) {
            throw IllegalStateException("You should call MonitorSdk.init() first !")
        }
        DataHelper.saveChannel(context!!, channel)
    }
}
