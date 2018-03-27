package com.heshidai.plugin.monitor.lifecycle.impl

import android.support.v4.app.Fragment
import com.heshidai.plugin.monitor.db.helper.TrackHelper

/**
 * Created by cool on 2018/3/7.
 */

open class MonitorSupportFragment : Fragment() {

    /**
     * If the fragment is not monitored, return false.
     */
    open var isNeedMonitor: Boolean = true

    private val isFragmentShown: Boolean
        get() = !isHidden && userVisibleHint

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //只有resumed状态的fragment适用此情景
        if (isNeedMonitor && isResumed) {
            // isVisibleToUser == true : FragmentShow
            //  isVisibleToUser == false : FragmentHide
            if (isVisibleToUser) {
                TrackHelper.get().startFragmentLifecycle(activity, this.javaClass.simpleName)
            } else {
                TrackHelper.get().endFragmentLifecycle()
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
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

    override fun onResume() {
        super.onResume()
        if (isNeedMonitor && isFragmentShown) {
            TrackHelper.get().startFragmentLifecycle(activity, this.javaClass.simpleName)
        }
    }

    override fun onPause() {
        super.onPause()
        if (isNeedMonitor && isFragmentShown) {
            TrackHelper.get().endFragmentLifecycle()
        }
    }
}
