package com.plugin.monitor.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by cool on 2018/3/7.
 */

object DateUtils {
    fun formatDate(date: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        return sdf.format(date)
    }
}
