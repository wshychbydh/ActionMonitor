package  com.heshidai.plugin.monitor.db.model

import com.google.gson.annotations.SerializedName

/**
 * Created by cool on 2018/3/1.
 */

internal class RequestHeader {
    @SerializedName("appInfo")
    var appInfo: AppInfo? = null
    @SerializedName("deviceInfo")
    var deviceInfo: DeviceInfo? = null
    @SerializedName("domain")
    var domain = "localhost"

    override fun toString(): String {
        return "RequestHeader(appInfo=$appInfo, deviceInfo=$deviceInfo, domain='$domain')"
    }
}
