package  com.heshidai.plugin.monitor.db.helper

import android.app.Activity
import android.content.Intent
import android.view.View
import com.heshidai.plugin.monitor.SyncService
import com.heshidai.plugin.monitor.db.model.PageAction
import com.heshidai.plugin.monitor.db.model.Track
import com.heshidai.plugin.monitor.util.LogUtils
import com.heshidai.plugin.monitor.util.Utils
import com.heshidai.plugin.monitor.util.ViewUtils

/**
 * Created by cool on 2018/3/7.
 */

internal class TrackHelper {

    private var track: Track? = null
    private var pageAction: PageAction? = null
    private var fragmentAction: PageAction? = null
    private var viewAction: MutableMap<View, PageAction>? = null

    private object SyncFactory {
        val instance = TrackHelper()
    }

    fun startTrack(activity: Activity) {
        val pageId = ViewUtils.getActivityPath(activity)
        //重新打开了第一个界面，视为轨迹结束，重新开始一个新的轨迹
        if (track?.isFinishedTrack(pageId) == true) {
            pageAction = DataFactory.createPageAction(activity)
            pageAction!!.pageStartTime = System.currentTimeMillis()
            track!!.actions.add(pageAction!!)
            endTrack(activity)
        }

        if (track == null) {
            track = Track()
            pageAction = DataFactory.createPageAction(activity)
            track!!.startPageId = pageId
            track!!.actions.add(pageAction!!)
        } else {
            if (pageAction == null || pageId != track!!.currentPageId) {
                pageAction = DataFactory.createPageAction(activity)
                track!!.actions.add(pageAction!!)
            }
        }
        track!!.currentPageId = pageId
        pageAction!!.pageStartTime = System.currentTimeMillis()
    }

    fun startTrack(activity: Activity, fragmentName: String) {
        if (fragmentAction == null) {
            fragmentAction = DataFactory.createPageAction(activity, fragmentName)
            track!!.actions.add(fragmentAction!!)
        }
        fragmentAction!!.pageStartTime = System.currentTimeMillis()
    }

    fun startPageAction(view: View) {
        if (viewAction == null) {
            viewAction = mutableMapOf()
        }
        val action = DataFactory.createPageAction(view)
        action.pageStartTime = System.currentTimeMillis()
        track!!.actions.add(action)
        viewAction!![view] = action
    }

    fun endPageAction(view: View) {
        if (viewAction!!.contains(view)) {
            val action = viewAction!![view]
            action!!.pageEndTime = System.currentTimeMillis()
            action.duration = System.currentTimeMillis() - fragmentAction!!.pageStartTime
            viewAction!!.remove(view)
        }
    }

    /**
     * call in fragment
     */
    fun tryEndTrack(activity: Activity) {
        pageAction!!.pageEndTime = System.currentTimeMillis()
        pageAction!!.duration = System.currentTimeMillis() - pageAction!!.pageStartTime
        if (Utils.isHome(activity)) {
            endTrack(activity)
        }
    }

    /**
     * call in fragment
     */
    fun tryEndTrack() {
        fragmentAction!!.pageEndTime = System.currentTimeMillis()
        fragmentAction!!.duration = System.currentTimeMillis() - fragmentAction!!.pageStartTime
    }

    private fun endTrack(activity: Activity) {
        LogUtils.d(track.toString())
        val intent = Intent(activity, SyncService::class.java)
        intent.putExtra(SyncService.TYPE, SyncService.TYPE_PAGE)
        intent.putParcelableArrayListExtra(SyncService.ACTION, track!!.actions)
        activity.startService(intent)
        pageAction = null
        fragmentAction = null
        viewAction = null
        track = null
    }

    companion object {
        fun get(): TrackHelper {
            return SyncFactory.instance
        }
    }
}
