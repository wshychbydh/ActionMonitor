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
        // Initialize Realm
//        Realm.init(context.applicationContext)
//        // Get a Realm instance for this thread
//        Realm.setDefaultConfiguration(makeConfig(context.applicationContext))
        //Upload data
        SqliteHelper.get()
        isSdkInited = true
        context.startService(Intent(context, SyncService::class.java))
    }

    //    private fun makeConfig(context: Context): RealmConfiguration {
//        return RealmConfiguration.Builder()
//                .name(context.packageName)
//                .schemaVersion(1)
//                //.modules(new MyOtherSchema())
//                .build()
//    }
//
    @JvmStatic
    fun release() {
//        if (isSdkInited) {
//             Realm.getDefaultInstance().close()
//        }
        context = null
        isSdkInited = false
    }
}
