package com.plugin.monitor.util

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.plugin.monitor.SyncService
import java.util.*

/**
 *Created by cool on 2018/6/25
 */
internal object ServiceUtil {

    fun startServiceWithAction(context: Context, value: ArrayList<out Parcelable>) {
        val intent = Intent(context, SyncService::class.java)
        intent.putExtra(SyncService.TYPE, SyncService.TYPE_PAGE)
        intent.putParcelableArrayListExtra(SyncService.ACTION, value)
        startSyncService(context, intent)
    }

    fun startServiceWithEvent(context: Context, value: Parcelable) {
        val intent = Intent(context, SyncService::class.java)
        intent.putExtra(SyncService.TYPE, SyncService.EVENT)
        intent.putExtra(SyncService.EVENT, value)
        startSyncService(context, intent)
    }

    fun startSyncService(context: Context, intent: Intent) {
        try {
            context.startService(intent)
            //FIXME build up 26 need this
           // ContextCompat.startForegroundService(context, intent)
        } catch (e: Exception) {
            //OPPO等手机处于省电模式时，息屏后会禁止“非允许”的程序后台运行
            LogUtils.e(e)
        }
    }
}