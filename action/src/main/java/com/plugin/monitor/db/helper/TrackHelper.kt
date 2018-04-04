package  com.plugin.monitor.db.helper

import android.app.Activity
import android.content.Intent
import com.plugin.monitor.SyncService
import com.plugin.monitor.db.model.PageAction
import com.plugin.monitor.db.model.Track
import com.plugin.monitor.util.LogUtils
import com.plugin.monitor.util.Utils
import com.plugin.monitor.util.ViewUtils

/**
 * Created by cool on 2018/3/7.
 */

internal class TrackHelper {
    private var track: Track? = null
    private var pageAction: PageAction? = null
    private var fragmentAction: PageAction? = null
    private var viewAction: MutableMap<String, PageAction>? = null

    private object SyncFactory {
        val instance = TrackHelper()
    }

    internal fun startTrack(activity: Activity) {
        val pageId = ViewUtils.getActivityPath(activity)
        //重新打开了第一个界面，视为轨迹结束，重新开始一个新的轨迹
        if (track?.isFinishedTrack(pageId) == true) {
            pageAction = DataFactory.createPageAction(activity, track!!.getPrePage())
            pageAction!!.pageStartTime = System.currentTimeMillis()
            track!!.actions.add(pageAction!!)
            endTrack(activity)
        }

        if (track == null) {
            track = Track()
            pageAction = DataFactory.createPageAction(activity, track!!.getPrePage())
            track!!.startPageId = pageId
            track!!.actions.add(pageAction!!)
        } else {
            if (pageAction == null || pageId != track!!.currentPageId) {
                pageAction = DataFactory.createPageAction(activity, track!!.getPrePage())
                track!!.actions.add(pageAction!!)
            }
        }
        track!!.currentPageId = pageId
        pageAction!!.pageStartTime = System.currentTimeMillis()
    }

    internal fun startFragmentLifecycle(activity: Activity, fragmentName: String) {
        if (fragmentAction == null) {
            fragmentAction = DataFactory.createPageAction(activity, fragmentName, track!!.getPrePage())
            track!!.actions.add(fragmentAction!!)
        }
        fragmentAction!!.pageStartTime = System.currentTimeMillis()
    }

    internal fun startPageAction(viewId: String) {
        if (viewAction == null) {
            viewAction = mutableMapOf()
        }
        val action = DataFactory.createPageViewAction(ViewUtils.decodeViewId(viewId), track!!.getPrePage())
        action.pageStartTime = System.currentTimeMillis()
        track!!.actions.add(action)
        viewAction!![viewId] = action
    }

    /**
     * View lifecycle is delayed.
     * may useless
     */
    internal fun endPageAction(viewId: String) {
        if (viewAction?.contains(viewId) == true) {
            val action = viewAction!![viewId]
            action!!.pageEndTime = System.currentTimeMillis()
            action.duration = System.currentTimeMillis() - action.pageStartTime
            viewAction!!.remove(viewId)
        }
    }

    /**
     * call in fragment
     */
    internal fun tryEndTrack(activity: Activity) {
        pageAction!!.pageEndTime = System.currentTimeMillis()
        pageAction!!.duration = System.currentTimeMillis() - pageAction!!.pageStartTime
        if (Utils.isHome(activity)) {
            endTrack(activity)
        }
    }

    /**
     * call in fragment
     */
    internal fun endFragmentLifecycle() {
        fragmentAction!!.pageEndTime = System.currentTimeMillis()
        fragmentAction!!.duration = System.currentTimeMillis() - fragmentAction!!.pageStartTime
    }

    /**
     * 因为view的onDetachedFromWindow会在activity的onPause之后调用
     */
    private fun tryEndViewLifecycle() {
        viewAction?.forEach {
            if (it.value.pageEndTime < 1) {
                it.value.pageEndTime = System.currentTimeMillis()
                it.value.duration = System.currentTimeMillis() - it.value.pageStartTime
            }
        }
    }

    private fun endTrack(activity: Activity) {
        tryEndViewLifecycle()
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
