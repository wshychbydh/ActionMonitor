package com.plugin.monitor.lifecycle

import android.app.Activity
import com.plugin.monitor.db.helper.TrackHelper

/**
 * Created by cool on 2018/3/22.
 */
internal interface FragmentLifecycle {
    /**
     * If the fragment is not monitored, return false.
     */
    var isNeedMonitor: Boolean
    val activity: Activity

    /**
     * isVisibleToUser: Fragment is visible to user
     */
    fun setUserVisibleHint(isVisibleToUser: Boolean) {
        //只有resumed状态的fragment适用此情景
        if (isNeedMonitor) {
            // isVisibleToUser == true : FragmentShow
            //  isVisibleToUser == false : FragmentHide
            if (isVisibleToUser) {
                TrackHelper.get().startFragmentLifecycle(activity, this.javaClass.simpleName)
            } else {
                TrackHelper.get().endFragmentLifecycle()
            }
        }
    }

    fun onHiddenChanged(hidden: Boolean) {
        if (isNeedMonitor) {
            //  hidden == true : FragmentShow
            //  hidden == false : FragmentHide
            if (hidden) {
                TrackHelper.get().startFragmentLifecycle(activity, this.javaClass.simpleName)
            } else {
                TrackHelper.get().endFragmentLifecycle()
            }
        }
    }

    fun onCreate() {
        if (isNeedMonitor) {
            TrackHelper.get().startFragmentLifecycle(activity, this.javaClass.simpleName)
        }
    }

    fun onResume() {
        if (isNeedMonitor) {
            TrackHelper.get().startFragmentLifecycle(activity, this.javaClass.simpleName)
        }
    }

    fun onPause() {
        if (isNeedMonitor) {
            TrackHelper.get().endFragmentLifecycle()
        }
    }
}