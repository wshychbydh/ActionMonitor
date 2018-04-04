package  com.plugin.monitor.lifecycle.impl

import android.app.Activity
import android.content.Context
import com.plugin.inject.Ignore
import com.plugin.monitor.lifecycle.ViewLifecycle

/**
 * Created by cool on 2018/3/15.
 */
class ViewLifecycleImpl(context: Context) : ViewLifecycle {

    override var isNeedMonitor: Boolean =
            if (context is Activity) {
                context.javaClass.getAnnotation(Ignore::class.java) == null
            } else true

    override var viewId: String = ""
}