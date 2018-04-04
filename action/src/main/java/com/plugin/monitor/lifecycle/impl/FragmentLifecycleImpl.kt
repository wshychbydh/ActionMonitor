package com.plugin.monitor.lifecycle.impl

import android.app.Activity
import com.plugin.inject.Ignore
import com.plugin.monitor.lifecycle.FragmentLifecycle

/**
 * Created by cool on 2018/3/22.
 */
class FragmentLifecycleImpl(override var activity: Activity) : FragmentLifecycle {

    /**
     * If you want to monitor even though activity is ignored, set it manual.
     */
    override var isNeedMonitor: Boolean = activity.javaClass.getAnnotation(Ignore::class.java) == null
}