package  com.plugin.monitor.proxy

import android.view.MotionEvent
import android.view.View
import com.plugin.monitor.util.Monitor
import java.lang.reflect.Method

/**
 * Created by cool on 2018/4/10.
 */

internal class TouchActionHandler internal constructor(target: View.OnTouchListener) : DefaultActionHandler(target) {

    init {
        addMethod("onTouch")
    }

    override fun doAction(obj: Any, method: Method, args: Array<Any>) {
        super.doAction(obj, method, args)
        Monitor.onTouch(args[0] as View, args[1] as MotionEvent)
    }
}
