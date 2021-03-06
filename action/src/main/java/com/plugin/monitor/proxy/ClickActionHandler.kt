package  com.plugin.monitor.proxy

import android.view.View
import com.plugin.monitor.util.Monitor
import java.lang.reflect.Method

/**
 * Created by cool on 2018/3/1.
 */

internal class ClickActionHandler internal constructor(target: View.OnClickListener) : DefaultActionHandler(target) {

    init {
        addMethod("onClick")
    }

    override fun doAction(obj: Any, method: Method, args: Array<Any>) {
        super.doAction(obj, method, args)
        Monitor.onClick(args[0] as View)
    }
}
