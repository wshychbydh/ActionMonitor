package com.plugin.monitor.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.plugin.monitor.util.LogUtils
import com.plugin.monitor.util.LooperThread


/**
 * Created by cool on 2018/3/23.
 */
internal object LocationHelper {

    fun startLocation(context: Context) {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //权限检查
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            LogUtils.e("请授权应用(${context.packageName})定位权限")
            return
        }
        if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {//是否支持Network定位
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location?) {
                            if (location != null) {
                                LogUtils.d("定位成功-->$location")
                                manager.removeUpdates(this)
                            }
                        }

                        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        }

                        override fun onProviderEnabled(provider: String?) {
                        }

                        override fun onProviderDisabled(provider: String?) {
                        }
                    })
        }
    }

    fun getLocation(context: Context): Location? {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            LogUtils.e("请授权应用(${context.packageName})定位权限")
            return null
        }
        //获取最后的network定位信息
        val location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location == null) {
            // must be run on handler thread
            LooperThread("location", {
                startLocation(context)
            }).start()
        }
        return location
    }
}