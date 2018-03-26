package  com.heshidai.plugin.monitor.db.model

import com.google.gson.annotations.SerializedName

/**
 * Created by cool on 2018/3/1.
 */

internal class RequestBody {
    @SerializedName("custom")
    var custom: Custom? = null
    @SerializedName("networkInfo")
    var networkInfo: NetworkInfo? = null
    @SerializedName("event")
    var eventAction: EventAction? = null
    @SerializedName("page")
    var pageActions: List<PageAction>? = null

    override fun toString(): String {
        return "RequestBody(custom=$custom, networkInfo=$networkInfo, eventAction=$eventAction, pageActions=$pageActions)"
    }
}
