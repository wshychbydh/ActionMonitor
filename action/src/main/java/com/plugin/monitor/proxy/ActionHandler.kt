package  com.plugin.monitor.proxy

import com.plugin.monitor.util.LogUtils
import java.lang.reflect.Method

/**
 * Created by cool on 2018/3/1.
 * proxy all interface's method
 */

internal class ActionHandler internal constructor(target: Any) : DefaultActionHandler(target) {

    init {
        target.javaClass.interfaces.forEach {
            it.methods.forEach { method ->
                addMethod(method.name)
            }
        }
    }

    override fun doAction(obj: Any, method: Method, args: Array<Any>) {
        super.doAction(obj, method, args)
        LogUtils.d("拦截到方法${method.name},但未做任何操作!")
        //FIXME
        // Monitor.onViewClick(args[0] as View)
    }
}
