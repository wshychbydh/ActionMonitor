package  com.plugin.monitor.db.model

/**
 * Created by cool on 2018/3/8.
 */
internal class ViewInfo {
    var viewType: String? = null
    var content: String? = null
    var path: String? = null
    override fun toString(): String {
        return "ViewInfo(viewType='$viewType', content='$content', path='$path')"
    }
}