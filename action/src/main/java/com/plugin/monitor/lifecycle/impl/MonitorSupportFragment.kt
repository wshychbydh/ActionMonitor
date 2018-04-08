package com.plugin.monitor.lifecycle.impl

import android.os.Bundle
import android.support.v4.app.Fragment
import com.plugin.inject.Ignore
import com.plugin.monitor.db.helper.TrackHelper

/**
 * Created by cool on 2018/3/7.
 * If your fragment is extends 'support.v4.app.Fragment'
 */

open class MonitorSupportFragment : Fragment() {

    /**
     * If the fragment is not monitored, return false.
     */
    open var isNeedMonitor: Boolean = true
        get() {
            return field && activity.javaClass.getAnnotation(Ignore::class.java) == null
        }

    private var isFragmentCreate: Boolean = false

    private val isFragmentShown: Boolean
        get() = !isHidden && userVisibleHint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isNeedMonitor && isFragmentShown) {
            TrackHelper.get().startFragmentLifecycle(activity, this.javaClass.simpleName)
        }
        isFragmentCreate = true
    }

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
