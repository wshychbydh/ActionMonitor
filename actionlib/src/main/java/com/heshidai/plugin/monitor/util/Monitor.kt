package com.heshidai.plugin.monitor.util

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import com.heshidai.plugin.monitor.SyncService
import com.heshidai.plugin.monitor.db.helper.DataFactory
import com.heshidai.plugin.monitor.db.model.EventAction
import io.reactivex.Observable

/**
 * Created by cool on 2018/3/7.
 */

object Monitor {

    @JvmStatic
    fun onViewClick(v: View) {
        Log.i("monitor", "ViewPath--onClick->" + ViewUtils.getViewPath(v))
        Log.i("monitor", "ViewInfo--onClick->" + ViewUtils.getViewInfo(v))
        Observable.create<EventAction> {
            it.onNext(DataFactory.createEventAction(v, EventAction.EVENT_CLICK))
        }.compose(RxUtils.applySchedulers()).subscribe({
            val intent = Intent(v.context, SyncService::class.java)
            intent.putExtra(SyncService.TYPE, SyncService.EVENT)
            intent.putExtra(SyncService.EVENT, it)
            v.context.startService(intent)
        })
    }

    @JvmStatic
    fun onViewLongClick(v: View) {
        Log.i("monitor", "ViewPath--onLongClick->" + ViewUtils.getViewPath(v))
        Log.i("monitor", "ViewInfo--onLongClick->" + ViewUtils.getViewInfo(v))
//        Observable.create<EventAction> {
//            it.onNext(DataFactory.createEventAction(v, EventAction.EVENT_LONG_CLICK))
//        }.compose(RxUtils.applySchedulers()).subscribe({
//            val intent = Intent(v.context, SyncService::class.java)
//            intent.putExtra(SyncService.TYPE, SyncService.EVENT)
//            intent.putExtra(SyncService.EVENT, it)
//            v.context.startService(intent)
//        })
    }

    /**
     * use for group
     */
    @JvmStatic
    fun onCheckChanged(group: RadioGroup, checkedId: Int) {
        Log.i("monitor", "ViewPath--onCheckChanged->" + ViewUtils.getViewPath(group))
        Log.i("monitor", "ViewInfo--onCheckChanged->" + ViewUtils.getViewInfo(group))
//        Observable.create<EventAction> {
//            it.onNext(DataFactory.createEventAction(group, EventAction.EVENT_CHECKED_CHANGED))
//        }.compose(RxUtils.applySchedulers()).subscribe({
//            val intent = Intent(group.context, SyncService::class.java)
//            intent.putExtra(SyncService.TYPE, SyncService.EVENT)
//            intent.putExtra(SyncService.EVENT, it)
//            group.context.startService(intent)
//        })
    }
}
