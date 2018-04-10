package com.plugin.monitor.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat
import android.telephony.TelephonyManager
import java.io.*
import java.net.*
import java.util.*


/**
 * Created by cool on 2018/3/1.
 */

object SystemUtils {

    /**
     * 获取外网的IP
     */
    fun getNetworkInfo(context: Context): String {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            LogUtils.e("请授权应用(${context.packageName})网络权限")
            return ""
        }
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

    val ipAddressString: String
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

    fun getImei(context: Context): String? {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            LogUtils.e("请授权应用(${context.packageName})读取手机状态权限")
            return ""
        }
        val tm = context.getSystemService(Activity.TELEPHONY_SERVICE) as? TelephonyManager
        // @TargetApi(Build.VERSION_CODES.O)
        // return tm?.imei ?: ""
        return tm?.deviceId ?: ""
    }

    fun getDeviceId(context: Context): String? {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            LogUtils.e("请授权应用(${context.packageName})读取手机状态权限")
            return ""
        }
        val tm = context.getSystemService(Activity.TELEPHONY_SERVICE) as? TelephonyManager
        return tm?.deviceId ?: ""
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

    fun getOperator(context: Context): String {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            LogUtils.e("请授权应用(${context.packageName})读取手机状态权限")
            return ""
        }
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as? TelephonyManager
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
        return ""
    }

    fun isWifiConnect(context: Context): Boolean {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            LogUtils.e("请授权应用(${context.packageName})读取网络状态权限")
            return false
        }
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val mWifi = connManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return mWifi?.isConnected ?: false
    }

    /**
     * 判断手机是否root，不弹出root请求框<br></br>
     */
    val isRoot: Boolean
        get() {
            val binPath = "/system/bin/su"
            val xBinPath = "/system/xbin/su"
            if (File(binPath).exists() && isExecutable(binPath))
                return true
            return File(xBinPath).exists() && isExecutable(xBinPath)
        }

    private fun isExecutable(filePath: String): Boolean {
        var p: Process? = null
        try {
            p = Runtime.getRuntime().exec("ls -l $filePath")
            // 获取返回内容
            val `in` = BufferedReader(InputStreamReader(
                    p!!.inputStream))
            val str = `in`.readLine()
            if (str != null && str.length >= 4) {
                val flag = str[3]
                if (flag == 's' || flag == 'x')
                    return true
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (p != null) {
                p.destroy()
            }
        }
        return false
    }
}
