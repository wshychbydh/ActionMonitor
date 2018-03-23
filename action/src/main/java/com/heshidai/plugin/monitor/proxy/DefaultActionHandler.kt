package  com.heshidai.plugin.monitor.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.*

internal open class DefaultActionHandler internal constructor(val target: Any) : InvocationHandler {

    private val methodList = ArrayList<String>()

    internal fun addMethod(methodName: String) {
        methodList.add(methodName)
    }

    @Throws(Throwable::class)
    override fun invoke(obj: Any, method: Method, args: Array<Any>): Any? {
        if (methodList.contains(method.name)) {
            doAction(obj, method, args)
        }
        return method.invoke(target, *args)
    }

    protected open fun doAction(obj: Any, method: Method, args: Array<Any>) {}

}