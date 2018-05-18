package com.plugin.monitor.location

import android.content.Context
import com.plugin.monitor.MonitorSdk
import com.plugin.monitor.location.model.NetInfo
import com.plugin.monitor.util.LogUtils
import com.plugin.monitor.util.SystemUtils
import com.plugin.monitor.util.Utils

/**
 * Created by cool on 2018/3/27.
 */
internal object NetworkHelper {

    fun requestNetworkInfo(context: Context) {
        Thread({
            if (SystemUtils.isWifiConnect(context)) {
                val netInfo = SystemUtils.getNetworkInfo(context)
                if (netInfo.isNotEmpty()) {
                    saveNetInfo(context, netInfo)
                }
            }
        }).start()
    }

    fun getNetInfo(): NetInfo? {
        val preferences = MonitorSdk.context!!.getSharedPreferences("NET_INFO", Context.MODE_PRIVATE)
        val json = preferences.getString("netInfo", null)
        if (json != null) {
            return try {
                Utils.getGson().fromJson(json, NetInfo::class.java)
            } catch (e: Exception) {
                LogUtils.e(e)
                null
            }
        }
        return null
    }

    private fun saveNetInfo(context: Context, netInfo: String) {
        LogUtils.d("保存网络信息-->$netInfo")
        val preferences = context.getSharedPreferences("NET_INFO", Context.MODE_PRIVATE)
        preferences.edit().putString("netInfo", netInfo).apply()
    }
}