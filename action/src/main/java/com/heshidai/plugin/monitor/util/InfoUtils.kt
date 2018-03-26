package com.heshidai.plugin.monitor.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.heshidai.plugin.monitor.db.model.AppInfo
import com.heshidai.plugin.monitor.db.model.DeviceInfo
import com.heshidai.plugin.monitor.db.model.NetworkInfo
import com.heshidai.plugin.monitor.location.LocationHelper
import java.util.*

/**
 * Created by cool on 2018/3/1.
 */

internal object InfoUtils {
    fun getAppInfo(context: Context): AppInfo {
        try {
            val pm = context.packageManager
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            val appInfo = AppInfo()
            appInfo.appVersion = packageInfo.versionName
            appInfo.sdkVersionName = packageInfo.versionName
            appInfo.sdkVersionCode = packageInfo.versionCode
            appInfo.channel = Utils.getAppMetaDataByKey(context, "channel")
            return appInfo
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            throw IllegalArgumentException("Can not get appInfo from " + context.packageName)
        }

    }

    fun getDeviceInfo(context: Context): DeviceInfo {
        val deviceInfo = DeviceInfo()
        deviceInfo.androidId = context.packageName
        val dm = context.resources.displayMetrics
        deviceInfo.density = dm.density
        deviceInfo.resolution = dm.widthPixels.toString() + " * " + dm.heightPixels
        deviceInfo.uuid = UUID.randomUUID().toString()
        deviceInfo.deviceId = SystemUtils.getDeviceId(context)
        deviceInfo.imei = SystemUtils.getIMEI(context)
        deviceInfo.locale = Locale.getDefault().toString()
        deviceInfo.mac = SystemUtils.getMacAddress()
        deviceInfo.model = Build.MODEL
        deviceInfo.os = "Android"
        deviceInfo.osVersion = Build.VERSION.RELEASE
        return deviceInfo
    }

    fun getNetworkInfo(context: Context): NetworkInfo {
        val networkInfo = NetworkInfo()
        networkInfo.carrier = SystemUtils.getOperator(context)
        networkInfo.ipAddress = SystemUtils.ipAddressString
        networkInfo.wifi = SystemUtils.isWifiConnect(context)
        val location = LocationHelper.getLocation(context)
        if (location != null) {
            networkInfo.latitude = location.latitude
            networkInfo.longitude = location.longitude
        }
        return networkInfo
    }
}
