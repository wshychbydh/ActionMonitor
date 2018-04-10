package  com.plugin.monitor.proxy

import android.widget.CompoundButton
import com.plugin.monitor.util.Monitor
import java.lang.reflect.Method

/**
 * Created by cool on 2018/4/10.
 */

internal class ButtonCheckedChangeActionHandler internal constructor(target: CompoundButton.OnCheckedChangeListener) : DefaultActionHandler(target) {

    init {
        addMethod("onCheckedChanged")
    }

    override fun doAction(obj: Any, method: Method, args: Array<Any>) {
        super.doAction(obj, method, args)
        Monitor.onCheckedChanged(args[0] as CompoundButton, args[1] as Boolean)
    }
}
