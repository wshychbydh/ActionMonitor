package  com.heshidai.plugin.monitor.db.helper

import android.app.Activity
import android.content.Context
import android.view.View
import com.heshidai.plugin.monitor.db.model.*
import com.heshidai.plugin.monitor.util.InfoUtils
import com.heshidai.plugin.monitor.util.Utils
import com.heshidai.plugin.monitor.util.ViewUtils

/**
 * Created by cool on 2018/3/1.
 */

internal object DataFactory {

    fun composeEventActionBody(context: Context, eventAction: EventAction): RequestBody {
        val body = RequestBody()
        body.eventAction = eventAction
        body.networkInfo = InfoUtils.getNetworkInfo(context)
        body.custom = Custom()
        body.custom!!.phone = DataHelper.getPhone(context)
        return body
    }

    fun composePageActionBody(context: Context, actions: List<PageAction>): RequestBody {
        val body = RequestBody()
        body.pageActions = actions
        body.networkInfo = InfoUtils.getNetworkInfo(context)
        body.custom = Custom()
        body.custom!!.phone = DataHelper.getPhone(context)
        return body
    }

    fun createHeader(context: Context): RequestHeader {
        val header = RequestHeader()
        header.appInfo = InfoUtils.getAppInfo(context)
        header.deviceInfo = InfoUtils.getDeviceInfo(context)
        val domain = Utils.getAppMetaDataByKey(context, "domain") ?: context.packageName
        header.domain = domain.replace(".", "")
        return header
    }

    fun createPageAction(activity: Activity, prePage: String?): PageAction {
        val action = PageAction()
        action.pageId = ViewUtils.getActivityPath(activity)
        action.referPageId = prePage
        return action
    }

    fun createPageAction(activity: Activity, fragmentName: String, prePage: String?): PageAction {
        val action = PageAction()
        action.pageId = ViewUtils.getFragmentPath(activity, fragmentName)
        action.referPageId = prePage
        return action
    }

    fun createPageViewAction(viewId: String, prePage: String?): PageAction {
        val action = PageAction()
        action.pageId = viewId
        action.referPageId = prePage
        return action
    }

    fun createEventAction(view: View, eventName: String): EventAction {
        val eventAction = EventAction()
        eventAction.actionTime = System.currentTimeMillis()
        eventAction.eventName = eventName
        eventAction.viewPath = ViewUtils.getViewPath(view)
        eventAction.viewInfo = ViewUtils.getViewInfo(view)
        if (view.context is Activity) {
            eventAction.activityName = Utils.getActivityFromView(view)
        }
        return eventAction
    }
}
