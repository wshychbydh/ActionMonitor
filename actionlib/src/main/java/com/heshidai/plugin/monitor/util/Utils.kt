package com.heshidai.plugin.monitor.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

/**
 * Created by cool on 2018/3/1.
 */

object Utils {

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

    //从View中利用context获取所属Activity的名字
    fun getActivityFromView(view: View): String? {
        val context = view.context
        if (context is Activity) {
            //context本身是Activity的实例
            return context.javaClass.name
        } else if (context is ContextWrapper) {
            //Activity有可能被系统＂装饰＂，看看context.base是不是Activity
            val activity = getActivityFromContextWrapper(context)
            return if (activity != null) {
                activity.javaClass.name
            } else {
                //如果从view.getContext()拿不到Activity的信息（比如view的context是Application）,则返回当前栈顶Activity的名字
                getTopActivity(context)
            }
        }
        return ""
    }

    private fun getActivityFromContextWrapper(wrapper: ContextWrapper): Activity? {
        var context = wrapper.baseContext
        while (context !is Activity && context is ContextWrapper) {
            context = context.baseContext
        }

        return if (context is Activity) {
            context
        } else null
    }

    /**
     * 获得栈中最顶层的Activity
     *
     * @param context
     * @return
     */
    private fun getTopActivity(context: Context): String? {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        val runningTaskInfo = manager.getRunningTasks(1)

        return if (runningTaskInfo != null) {
            runningTaskInfo[0].topActivity.toString()
        } else {
            null
        }
    }

    fun reflectGetReferrer(activity: Activity): String? {
        val name = activity.callingActivity?.toString()
        if (name != null) {
            return name
        }
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                val activityClass = Class.forName("android.app.Activity")
                val referField = activityClass.getDeclaredField("mReferrer")
                referField?.isAccessible = true
                return referField?.get(activity) as? String
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            }
        }
        return ""
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

    /**
     * 权限:<uses-permission android:name="android.permission.GET_TASKS"></uses-permission>
     * 判断当前界面是否是桌面 ,先获取桌面应用的程序包名,然后判断当前显示活动包名是否包含在内
     * API14以后可以通过Application的生命周期回调来判断
     * public void onTrimMemory(int level) {
     *  super.onTrimMemory(level);
     *  if (level == TRIM_MEMORY_UI_HIDDEN) {
     *    isBackground = true;
     *    notifyBackground();
     *  }

     */

    fun isHome(context: Context): Boolean {
        val mActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val rti = mActivityManager.getRunningTasks(1)
        return getHomes(context).contains(rti[0].topActivity.packageName)
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有桌面应用的包名的字符串列表
     */
    private fun getHomes(context: Context): List<String> {
        val names = ArrayList<String>()
        val packageManager = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY)
        for (ri in resolveInfo) {
            names.add(ri.activityInfo.packageName)
            //属于桌面的应用:com.android.launcher(启动器)
            println("属于桌面的应用:" + ri.activityInfo.packageName)

        }
        return names
    }

    fun getActivityPath(activity: Activity): String {
        return activity.packageName + "." + activity.localClassName
    }

    fun getFragmentPath(activity: Activity, fragmentName: String): String {
        return getActivityPath(activity) + " / " + fragmentName
    }

    fun getViewPath(view: View): String {
        val context = view.context
        return if (context is Activity) {
            getActivityPath(view.context as Activity) + " / " + view.javaClass.simpleName
        } else {
            ViewUtils.getViewPath(view)
        }
    }
}
