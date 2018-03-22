package  com.heshidai.plugin.monitor.db.model

class AppInfo {
    var appVersion: String? = null
    var channel: String? = null
    var sdkVersionCode: Int = 0
    var sdkVersionName: String? = null

    override fun toString(): String {
        return "AppInfo{" +
                ", appVersion='" + appVersion + '\''.toString() +
                ", channel='" + channel + '\''.toString() +
                ", sdkVersionCode='" + sdkVersionCode + '\''.toString() +
                ", sdkVersionName='" + sdkVersionName + '\''.toString() +
                '}'.toString()
    }
}