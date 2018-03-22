package com.heshidai.plugin.monitor.util

import android.util.Log

/**
 * Created by cool on 2018/3/9.
 */
object LogUtils {
    private var isDebug = false
    private const val TAG = "Monitor"

    fun setDebugAble(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    /**
     * info.
     *
     * @param message the message
     */
    fun i(message: String) {
        i(TAG, message)
    }

    /**
     * 记录“i”级别的信息
     *
     * @param tag the tag
     * @param msg the msg
     */
    fun i(tag: String, msg: String) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    /**
     * Verbose.
     *
     * @param message the message
     */
    fun v(message: String) {
        v(TAG, message)
    }

    /**
     * 记录“v”级别的信息
     *
     * @param tag the tag
     * @param msg the msg
     */
    fun v(tag: String, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    /**
     * Debug.
     *
     * @param message the message
     */
    fun d(message: String) {
        d(TAG, message)
    }

    /**
     * 记录“d”级别的信息
     *
     * @param tag the tag
     * @param msg the msg
     */
    fun d(tag: String, msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    /**
     * Warn.
     *
     * @param e the e
     */
    fun w(e: Throwable) {
        w(e.message ?: "")
    }

    /**
     * Warn.
     *
     * @param message the message
     */
    fun w(message: String) {
        w(TAG, message)
    }

    /**
     * 记录“w”级别的信息
     *
     * @param tag the tag
     * @param msg the msg
     */
    fun w(tag: String, msg: String) {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }

    /**
     * Error.
     *
     * @param e the e
     */
    fun e(e: Throwable) {
        e(e.message ?: "")
    }

    /**
     * Error.
     *
     * @param message the message
     */
    fun e(message: String) {
        e(TAG, message)
    }

    /**
     * 记录“e”级别的信息
     *
     * @param tag the tag
     * @param msg the msg
     */
    fun e(tag: String, msg: String) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }
}