package com.heshidai.plugin.monitor.util

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.telephony.TelephonyManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.net.*
import java.util.*


/**
 * Created by cool on 2018/3/1.
 */

object SystemUtils {

    /**
     * 获取外网的IP
     */
    fun getNetworkInfo(): String {
        val infoUrl = URL("http://pv.sohu.com/cityjson?ie=utf-8")
        val connection = infoUrl.openConnection()
        val httpConnection = connection as HttpURLConnection
        val responseCode = httpConnection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val stream = httpConnection.inputStream
            stream.use { it ->
                val reader = BufferedReader(InputStreamReader(it, "utf-8"))
                val sb = StringBuilder()
                var line: String?
                do {
                    line = reader.readLine()
                    if (line != null) {
                        sb.append(line + "\n")
                    }
                } while (line != null)
                val start = sb.indexOf("{")
                val end = sb.indexOf("}")
                return sb.substring(start, end + 1)
            }
        }
        return ""
    }

    /**
     * 获取本地IP
     */

    internal val ipAddressString: String
        get() {
            try {
                val enNetI = NetworkInterface
                        .getNetworkInterfaces()
                while (enNetI.hasMoreElements()) {
                    val netI = enNetI.nextElement()
                    val enumIpAddr = netI.inetAddresses
                    while (enumIpAddr.hasMoreElements()) {
                        val address = enumIpAddr.nextElement()
                        if (address is Inet4Address && !address.isLoopbackAddress()) {
                            return address.getHostAddress()
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

    fun getMacAddress(): String {
        val all = Collections.list(NetworkInterface.getNetworkInterfaces())
        for (nif in all) {
            if (!nif.name.equals("wlan0", ignoreCase = true)) continue
            val macBytes = nif.hardwareAddress ?: return ""
            val res1 = StringBuilder()
            for (b in macBytes) {
                res1.append(String.format("%02X:", b))
            }
            if (res1.isNotEmpty()) {
                res1.deleteCharAt(res1.length - 1)
            }
            return res1.toString()
        }
        return "02:00:00:00:00:00"
    }

    /**
     * 这是使用adb shell命令来获取mac地址的方式
     * @return
     */
    fun getMac(): String? {
        var macSerial: String? = null
        var str: String? = ""
        try {
            val pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address ")
            val ir = InputStreamReader(pp.inputStream)
            val input = LineNumberReader(ir)

            while (null != str) {
                str = input.readLine()
                if (str != null) {
                    macSerial = str.trim { it <= ' ' }// 去空格
                    break
                }
            }
        } catch (ex: IOException) {
            // 赋予默认值
            ex.printStackTrace()
        }
        return macSerial
    }

    @SuppressLint("MissingPermission", "HardwareIds")
    internal fun getOperator(context: Context): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
        try {
            val IMSI = telephonyManager?.subscriberId
            LogUtils.d("运营商代码" + IMSI!!)
            if (!IMSI.isNullOrEmpty()) {
                return if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
                    "中国移动"
                } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
                    "中国联通"
                } else if (IMSI.startsWith("46003")) {
                    "中国电信"
                } else {
                    ""
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
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
