package  com.heshidai.plugin.monitor.db.model

class Parameter {
    var name: String? = null
    var value: String? = null
    var referPageId: String? = null
    var exception: String? = null
    override fun toString(): String {
        return "Parameter(name=$name, value=$value, referPageId=$referPageId, exception=$exception)"
    }
}