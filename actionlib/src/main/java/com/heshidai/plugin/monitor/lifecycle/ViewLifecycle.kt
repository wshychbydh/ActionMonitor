package  com.heshidai.plugin.monitor.lifecycle

import android.view.View
import com.heshidai.plugin.monitor.db.helper.TrackHelper

/**
 * Created by cool on 2018/3/1.
 * @See ViewLifecycleImpl
 */

interface ViewLifecycle {

    var visible: Int

    fun onAttached() {
        visible = View.VISIBLE
    }

    fun onDetached() {
        visible = View.GONE
    }

    fun onVisibilityChanged(view: View) {
        if (visible != view.visibility) {
            visible = view.visibility
            if (visible == View.GONE) {
                TrackHelper.get().endPageAction(view)
            } else {
                TrackHelper.get().startPageAction(view)
            }
        }
    }
}