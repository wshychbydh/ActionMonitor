package com.heshidai.plugin.monitor.proxy

import android.view.View

import java.lang.reflect.Proxy

/**
 * Created by cool on 2018/3/1.
 */

object ProxyHelper {

    fun proxy(listener: View.OnClickListener): View.OnClickListener {
        return Proxy.newProxyInstance(View.OnClickListener::class.java.classLoader,
                arrayOf<Class<*>>(View.OnClickListener::class.java), ClickActionHandler(listener)) as View.OnClickListener
    }

    fun proxy(listener: View.OnLongClickListener): View.OnLongClickListener {
        return Proxy.newProxyInstance(View.OnClickListener::class.java.classLoader,
                arrayOf<Class<*>>(View.OnLongClickListener::class.java), LongClickActionHandler(listener)) as View.OnLongClickListener
    }

    fun <T> proxy(listener: Class<*>): T {
        if (!listener.isInterface) {
            throw IllegalArgumentException("listener must be implement one or more interface!")
        }
        return Proxy.newProxyInstance(listener.classLoader, listener.interfaces, ActionHandler(listener)) as T
    }
}
