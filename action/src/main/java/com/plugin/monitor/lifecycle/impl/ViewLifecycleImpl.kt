package  com.plugin.monitor.lifecycle.impl

import android.app.Activity
import android.content.Context
import com.plugin.monitor.BuildConfig
import com.plugin.monitor.annotation.Ignore
import com.plugin.monitor.lifecycle.ViewLifecycle

/**
 * Created by cool on 2018/3/15.
 */
class ViewLifecycleImpl(val context: Context?) : ViewLifecycle {

    override var isNeedMonitor: Boolean = true
        get() {
            if (context == null || BuildConfig.DEBUG) return false
            return if (context is Activity) {
                field && context.javaClass.getAnnotation(Ignore::class.java) == null
            } else field
        }
    override var viewId: String = ""
}