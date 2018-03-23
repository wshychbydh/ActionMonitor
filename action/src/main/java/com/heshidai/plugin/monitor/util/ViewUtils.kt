package com.heshidai.plugin.monitor.util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.Gson
import com.heshidai.plugin.monitor.db.model.ViewInfo
import java.util.*

/**
 * Created by cool on 2018/3/8.
 */
object ViewUtils {

    fun getActivityPath(activity: Activity): String {
        return activity.packageName + "." + activity.localClassName
    }

    fun getFragmentPath(activity: Activity, fragmentName: String): String {
        return getActivityPath(activity) + " / " + fragmentName
    }

    fun getViewFullPath(view: View): String {
        val context = view.context
        return if (context is Activity) {
            getActivityPath(view.context as Activity) + " / " + view.javaClass.simpleName
        } else {
            getViewPath(view)
        }
    }

    fun getViewPath(view: View): String {
        val path = view.javaClass.simpleName
        val parent = view.parent
        if (parent is ViewGroup) {
            val index = parent.indexOfChild(view)
            return getViewPath(parent as View) + "/$path[$index]"
        }
        return path
    }

    fun getViewInfo(view: View): String {
        val infoList = ArrayList<ViewInfo>()
        val info = ViewInfo()
        info.viewType = view.javaClass.simpleName

        if (view is ViewGroup) {
            info.path = view.javaClass.simpleName
            getViewInfoFromViewGroup(infoList, view, "${info.path}[0]")
        } else {
            if (view is TextView) {
                info.content = view.text.toString()
            }
            infoList.add(info)
        }
        return Gson().toJson(infoList)
    }

    private fun getViewInfoFromViewGroup(infoList: ArrayList<ViewInfo>, group: ViewGroup, path: String): List<ViewInfo> {
        (0 until group.childCount)
                .map { group.getChildAt(it) }
                .forEachIndexed { index, view ->
                    if (view is TextView) {
                        val info = ViewInfo()
                        info.viewType = view.javaClass.simpleName
                        info.content = view.text.toString()
                        info.path = "$path/${info.viewType}[$index]"
                        infoList.add(info)
                    } else if (view is ViewGroup) {
                        getViewInfoFromViewGroup(infoList, view, "$path/${view.javaClass.simpleName}[$index]")
                    }
                }
        return infoList
    }
}