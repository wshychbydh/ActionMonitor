package com.plugin.monitor.proxy

import android.view.View
import com.plugin.monitor.util.Monitor
import java.lang.reflect.Method

/**
 * Created by cool on 2018/3/1.
 */

internal class LongClickActionHandler internal constructor(target: View.OnLongClickListener) : DefaultActionHandler(target) {

    init {
        addMethod("onLongClick")
    }

    override fun doAction(obj: Any, method: Method, args: Array<Any>) {
        super.doAction(obj, method, args)
        Monitor.onLongClick(args[0] as View)
    }
}
