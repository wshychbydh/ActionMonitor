package  com.heshidai.plugin.monitor.lifecycle.impl

import android.view.View
import com.heshidai.plugin.monitor.lifecycle.ViewLifecycle

/**
 * Created by cool on 2018/3/15.
 */
class ViewLifecycleImpl : ViewLifecycle {
    override var visible: Int = View.VISIBLE

    override fun onAttached() {
        super.onAttached()
    }

    override fun onDetached() {
        super.onDetached()
    }

    override fun onVisibilityChanged(view: View) {
        super.onVisibilityChanged(view)
    }
}