package com.heshidai.plugin.monitor.util

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

/**
 * Created by cool on 2018/3/1.
 */

object SystemUtils {

    internal val ipAddressString: String
        get() {
            try {
                val enNetI = NetworkInterface
                        .getNetworkInterfaces()
                while (enNetI.hasMoreElements()) {
                    val netI = enNetI.nextElement()
                    val enumIpAddr = netI
                            .inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val inetAddress = enumIpAddr.nextElement()
                        if (inetAddress is Inet4Address && !inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress()
                        }
                    }
                }
            } catch (e: SocketException) {
                e.printStackTrace()
            }

            return ""
        }

    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.O)
    internal fun getIMEI(ctx: Context): String? {
        val tm = ctx.getSystemService(Activity.TELEPHONY_SERVICE) as? TelephonyManager
        if (tm != null) {
            return try {
                tm.imei
            } catch (e: Exception) {
                null
            }

        }
        return null
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    internal fun getDeviceId(ctx: Context): String? {
        val tm = ctx.getSystemService(Activity.TELEPHONY_SERVICE) as? TelephonyManager
        if (tm != null) {
            return try {
                tm.deviceId
            } catch (e: Exception) {
                null
            }

        }
        return null
    }

    /**
     * FIXME 6.0以上可能返回02:00:00:00:00:00
     *
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds", "MissingPermission")
    internal fun getMac(context: Context): String {
        val wifiMan = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInf = wifiMan.connectionInfo
        return wifiInf.macAddress
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    internal fun getOperator(context: Context): String {
        var providerName = ""
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
        try {
            val IMSI = telephonyManager?.subscriberId
            Log.i("qweqwes", "运营商代码" + IMSI!!)
            if (!IMSI.isNullOrEmpty()) {
                if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
                    providerName = "中国移动"
                } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
                    providerName = "中国联通"
                } else if (IMSI.startsWith("46003")) {
                    providerName = "中国电信"
                }
                return providerName
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "未获取到sim卡信息"
    }

    @SuppressLint("MissingPermission")
    internal fun isWifiConnect(context: Context): Boolean {
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val mWifi = connManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return mWifi?.isConnected ?: false
    }

    @SuppressLint("MissingPermission")
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        if (connectivityManager == null) {
            return false
        } else {
            val networkInfo = connectivityManager.allNetworkInfo
            if (networkInfo?.isNotEmpty() == true) {
                networkInfo.forEach {
                    if (it.state == NetworkInfo.State.CONNECTED) return true
                }
            }
        }
        return false
    }
}
