package  com.plugin.monitor.db.model

internal class DeviceInfo {
    var density: Float = 0.toFloat()
    var deviceId: String? = null
    var imei: String? = null
    var locale: String? = null
    var mac: String? = null
    var model: String? = null
    var os: String? = null
    var osVersion: String? = null
    var resolution: String? = null
    var uuid: String? = null
    override fun toString(): String {
        return "DeviceInfo(density=$density, deviceId=$deviceId, imei=$imei, locale=$locale, mac=$mac, model=$model, os=$os, osVersion=$osVersion, resolution=$resolution, uuid=$uuid)"
    }
}