package com.plugin.monitor.proxy

import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup

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

    fun proxy(listener: View.OnTouchListener): View.OnTouchListener {
        return Proxy.newProxyInstance(View.OnTouchListener::class.java.classLoader,
                arrayOf<Class<*>>(View.OnTouchListener::class.java), TouchActionHandler(listener)) as View.OnTouchListener
    }

    fun proxy(listener: RadioGroup.OnCheckedChangeListener): RadioGroup.OnCheckedChangeListener {
        return Proxy.newProxyInstance(RadioGroup.OnCheckedChangeListener::class.java.classLoader,
                arrayOf<Class<*>>(RadioGroup.OnCheckedChangeListener::class.java), GroupCheckedChangeActionHandler(listener)) as RadioGroup.OnCheckedChangeListener
    }

    fun proxy(listener: CompoundButton.OnCheckedChangeListener): CompoundButton.OnCheckedChangeListener {
        return Proxy.newProxyInstance(CompoundButton.OnCheckedChangeListener::class.java.classLoader,
                arrayOf<Class<*>>(CompoundButton.OnCheckedChangeListener::class.java), ButtonCheckedChangeActionHandler(listener)) as CompoundButton.OnCheckedChangeListener
    }

    fun <T> proxy(listener: Class<T>): T {
        if (!listener.isInterface) {
            throw IllegalArgumentException("listener must be implement one or more interface!")
        }
        return Proxy.newProxyInstance(listener.classLoader, listener.interfaces, ActionHandler(listener)) as T
    }
}
