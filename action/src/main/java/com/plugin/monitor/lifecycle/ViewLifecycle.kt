package  com.plugin.monitor.lifecycle

import android.view.View
import com.plugin.monitor.db.helper.TrackHelper
import com.plugin.monitor.util.ViewUtils

/**
 * Created by cool on 2018/3/1.
 * @See ViewLifecycleImpl
 */

internal interface ViewLifecycle {

    var viewId: String

    var isNeedMonitor: Boolean

    fun onAttached(view: View) {
        if (isNeedMonitor) {
            viewId = ViewUtils.encodeViewId(ViewUtils.getViewFullPath(view))
        }
    }

    fun onDetached() {
        if (isNeedMonitor) {
            TrackHelper.get().endPageAction(viewId)
        }
    }

    fun onVisibilityChanged(visibility: Int) {
        if (viewId.isEmpty() || !isNeedMonitor) return
        if (visibility == View.VISIBLE) {
            TrackHelper.get().startPageAction(viewId)
        } else {
            TrackHelper.get().endPageAction(viewId)
        }
    }
}