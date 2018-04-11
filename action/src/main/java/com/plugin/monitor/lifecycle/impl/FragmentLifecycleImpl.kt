package com.plugin.monitor.lifecycle.impl

import android.app.Activity
import com.plugin.monitor.annotation.Ignore
import com.plugin.monitor.lifecycle.FragmentLifecycle

/**
 * Created by cool on 2018/3/22.
 */
class FragmentLifecycleImpl(override val activity: Activity) : FragmentLifecycle {

    /**
     * If you want to monitor even though activity is ignored, set it manual.
     */
    override var isNeedMonitor: Boolean = activity.javaClass.getAnnotation(Ignore::class.java) == null

    private var isHidden: Boolean = false
    private var isVisibleToUser: Boolean = true
    private var isFragmentCreate: Boolean = false
    private var isFragmentResumed: Boolean = false
    private val isFragmentShown: Boolean
        get() = !isHidden && isVisibleToUser

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        this.isVisibleToUser = isVisibleToUser
        if (isFragmentResumed) {
            super.setUserVisibleHint(isVisibleToUser)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        this.isHidden = hidden
        super.onHiddenChanged(hidden)
    }

    override fun onCreate() {
        if (isFragmentShown) {
            super.onCreate()
        }
        isFragmentCreate = true
    }

    override fun onResume() {
        if (!isFragmentCreate && isFragmentShown) {
            super.onResume()
        }
        isFragmentCreate = false
        isFragmentResumed = true
    }

    override fun onPause() {
        if (isFragmentShown) {
            super.onPause()
        }
        this.isFragmentResumed = true
    }
}