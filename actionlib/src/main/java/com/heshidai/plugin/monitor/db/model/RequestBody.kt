package  com.heshidai.plugin.monitor.db.model

import com.google.gson.annotations.SerializedName

/**
 * Created by cool on 2018/3/1.
 */

class RequestBody {

    @SerializedName("networkInfo")
    var networkInfo: NetworkInfo? = null
    @SerializedName("event")
    var eventAction: EventAction? = null
    @SerializedName("exceptionInfo")
    var exception: String? = null
    @SerializedName("page")
    var pageActions: List<PageAction>? = null

    override fun toString(): String {
        return "RequestBody(networkInfo=$networkInfo, eventAction=$eventAction, exception=$exception, pageActions=$pageActions)"
    }
}
