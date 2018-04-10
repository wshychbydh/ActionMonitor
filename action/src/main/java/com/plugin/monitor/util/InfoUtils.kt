package com.plugin.monitor.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.plugin.monitor.db.model.AppInfo
import com.plugin.monitor.db.model.DeviceInfo
import com.plugin.monitor.db.model.NetworkInfo
import com.plugin.monitor.location.LocationHelper
import com.plugin.monitor.location.NetworkHelper
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
            val channel = Utils.getAppMetaDataByKey(context, "channel")
            appInfo.channel = channel ?: Utils.getAppMetaDataByKey(context, "UMENG_CHANNEL")
            return appInfo
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            throw IllegalArgumentException("Can not get appInfo from " + context.packageName)
        }

    }

    fun getDeviceInfo(context: Context): DeviceInfo {
        val deviceInfo = DeviceInfo()
        val dm = context.resources.displayMetrics
        deviceInfo.density = dm.density
        deviceInfo.resolution = dm.widthPixels.toString() + " * " + dm.heightPixels
        deviceInfo.uuid = UUID.randomUUID().toString()
        deviceInfo.deviceId = SystemUtils.getDeviceId(context)
        deviceInfo.imei = SystemUtils.getImei(context)
        deviceInfo.locale = Locale.getDefault().toString()
        deviceInfo.mac = SystemUtils.getMacAddress()
        deviceInfo.model = Build.MODEL
        deviceInfo.os = "Android"
        deviceInfo.osVersion = Build.VERSION.RELEASE
        return deviceInfo
    }

    fun getNetworkInfo(context: Context): NetworkInfo {
        val networkInfo = NetworkInfo()
        val netInfo = NetworkHelper.getNetInfo()
        networkInfo.ipAddress = if (netInfo != null) netInfo.cip else SystemUtils.ipAddressString
        networkInfo.carrier = SystemUtils.getOperator(context)
        networkInfo.wifi = if (SystemUtils.isWifiConnect(context)) 1 else 0
        val location = LocationHelper.getLocation(context)
        if (location != null) {
            networkInfo.latitude = location.latitude
            networkInfo.longitude = location.longitude
        }
        return networkInfo
    }
}
