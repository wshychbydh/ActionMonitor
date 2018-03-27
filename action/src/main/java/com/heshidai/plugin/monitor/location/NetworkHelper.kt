package com.heshidai.plugin.monitor.location

import android.content.Context
import com.heshidai.plugin.monitor.MonitorSdk
import com.heshidai.plugin.monitor.location.model.NetInfo
import com.heshidai.plugin.monitor.util.LogUtils
import com.heshidai.plugin.monitor.util.SystemUtils
import com.heshidai.plugin.monitor.util.Utils

/**
 * Created by cool on 2018/3/27.
 */
object NetworkHelper {

    fun requestNetworkInfo(context: Context) {
        Thread({
            if (SystemUtils.isWifiConnect(context)) {
                val netInfo = SystemUtils.getNetworkInfo()
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
            return Utils.getGson().fromJson(json, NetInfo::class.java)
        }
        return null
    }

    private fun saveNetInfo(context: Context, netInfo: String) {
        LogUtils.d("保存网络信息-->$netInfo")
        val preferences = context.getSharedPreferences("NET_INFO", Context.MODE_PRIVATE)
        preferences.edit().putString("netInfo", netInfo).apply()
    }
}