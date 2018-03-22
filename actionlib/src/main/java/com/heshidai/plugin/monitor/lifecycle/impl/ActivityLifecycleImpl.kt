package  com.heshidai.plugin.monitor.lifecycle.impl

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.heshidai.plugin.monitor.db.helper.TrackHelper

/**
 * Created by cool on 2018/3/1.
 */

class ActivityLifecycleImpl : Application.ActivityLifecycleCallbacks {


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {
        TrackHelper.get().startTrack(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        TrackHelper.get().tryEndTrack(activity)
    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}
