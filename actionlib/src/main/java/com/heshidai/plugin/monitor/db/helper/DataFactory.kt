package  com.heshidai.plugin.monitor.db.helper

import android.app.Activity
import android.content.Context
import android.view.View
import com.heshidai.plugin.monitor.db.model.EventAction
import com.heshidai.plugin.monitor.db.model.PageAction
import com.heshidai.plugin.monitor.db.model.RequestBody
import com.heshidai.plugin.monitor.db.model.RequestHeader
import com.heshidai.plugin.monitor.util.DateUtils
import com.heshidai.plugin.monitor.util.InfoUtils
import com.heshidai.plugin.monitor.util.Utils
import com.heshidai.plugin.monitor.util.ViewUtils

/**
 * Created by cool on 2018/3/1.
 */

object DataFactory {

    fun composeEventActionBody(context: Context, eventAction: EventAction): RequestBody {
        val body = RequestBody()
        body.eventAction = eventAction
        body.networkInfo = InfoUtils.getNetworkInfo(context)
        return body
    }

    fun composePageActionBody(context: Context, pageAction: PageAction): RequestBody {
        return composePageActionBody(context, arrayListOf(pageAction))
    }

    fun composePageActionBody(context: Context, actions: List<PageAction>): RequestBody {
        val body = RequestBody()
        body.pageActions = actions
        body.networkInfo = InfoUtils.getNetworkInfo(context)
        return body
    }

    fun createHeader(context: Context): RequestHeader {
        val header = RequestHeader()
        header.appInfo = InfoUtils.getAppInfo(context)
        header.deviceInfo = InfoUtils.getDeviceInfo(context)
       // header.domain = context.packageName
        return header
    }

    fun createPageAction(activity: Activity): PageAction {
        val action = PageAction()
        action.pageId = Utils.getActivityPath(activity)
        action.referPageId = Utils.reflectGetReferrer(activity)
        return action
    }

    fun createPageAction(activity: Activity, fragmentName: String): PageAction {
        val action = PageAction()
        action.pageId = Utils.getFragmentPath(activity, fragmentName)
        action.referPageId = Utils.reflectGetReferrer(activity)
        return action
    }

    fun createPageAction(view: View): PageAction {
        val action = PageAction()
        action.pageId = Utils.getViewPath(view)
        if (view.context is Activity) {
            action.referPageId = Utils.reflectGetReferrer(view.context as Activity)
        }
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
