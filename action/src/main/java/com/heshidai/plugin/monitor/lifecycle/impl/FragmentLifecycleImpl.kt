package com.heshidai.plugin.monitor.lifecycle.impl

import android.app.Activity
import com.heshidai.plugin.monitor.lifecycle.FragmentLifecycle

/**
 * Created by cool on 2018/3/22.
 */
class FragmentLifecycleImpl(override var isNeedMonitor: Boolean = true, override var activity: Activity) : FragmentLifecycle