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

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
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