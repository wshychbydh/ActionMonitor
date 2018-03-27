package  com.heshidai.plugin.monitor.lifecycle

import android.view.View
import com.heshidai.plugin.monitor.db.helper.TrackHelper
import com.heshidai.plugin.monitor.util.ViewUtils

/**
 * Created by cool on 2018/3/1.
 * @See ViewLifecycleImpl
 */

interface ViewLifecycle {

    var viewId: String

    fun onAttached(view: View) {
        viewId = ViewUtils.encodeViewId(ViewUtils.getViewFullPath(view))
    }

    fun onDetached() {
        TrackHelper.get().endPageAction(viewId)
    }

    fun onVisibilityChanged(visibility: Int) {
        if (viewId.isEmpty()) return
        if (visibility == View.VISIBLE) {
            TrackHelper.get().startPageAction(viewId)
        } else {
            TrackHelper.get().endPageAction(viewId)
        }
    }
}