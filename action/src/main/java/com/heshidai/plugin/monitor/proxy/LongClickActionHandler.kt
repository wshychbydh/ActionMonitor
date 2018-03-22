package com.heshidai.plugin.monitor.proxy

import android.view.View
import com.heshidai.plugin.monitor.util.Monitor
import java.lang.reflect.Method

/**
 * Created by cool on 2018/3/1.
 */

class LongClickActionHandler internal constructor(target: Any) : DefaultActionHandler(target) {

    override fun doAction(obj: Any, method: Method, args: Array<Any>) {
        super.doAction(obj, method, args)
        Monitor.onViewLongClick(args[0] as View)
    }
}
