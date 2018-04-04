package  com.plugin.monitor.lifecycle.impl

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.plugin.inject.Ignore
import com.plugin.monitor.db.helper.TrackHelper

/**
 * Created by cool on 2018/3/1.
 */

internal class ActivityLifecycleImpl : Application.ActivityLifecycleCallbacks {

    //用于保存轨迹的第一个Page是Activity，而不会是View或Fragment
    private var isActivityCreated = false

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity.javaClass.getAnnotation(Ignore::class.java) == null) {
            TrackHelper.get().startTrack(activity)
            isActivityCreated = true
        }
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        if (isActivityCreated) {
            isActivityCreated = false
            return
        }
        if (activity.javaClass.getAnnotation(Ignore::class.java) == null) {
            TrackHelper.get().startTrack(activity)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity.javaClass.getAnnotation(Ignore::class.java) == null) {
            TrackHelper.get().tryEndTrack(activity)
        }
    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}
