package com.plugin.monitor.util

import android.content.Intent
import android.view.MotionEvent
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.plugin.monitor.SyncService
import com.plugin.monitor.db.helper.DataFactory
import com.plugin.monitor.db.model.EventAction

/**
 * Created by cool on 2018/3/7.
 */

object Monitor {

    @JvmStatic
    fun onViewClick(v: View) {
        LogUtils.d("monitor", "ViewPath--onClick->" + ViewUtils.getViewPath(v))
        LogUtils.d("monitor", "ViewInfo--onClick->" + ViewUtils.getViewInfo(v))

        ThreadUtils.execute {
            val action = DataFactory.createEventAction(v, EventAction.EVENT_CLICK)
            val intent = Intent(v.context, SyncService::class.java)
            intent.putExtra(SyncService.TYPE, SyncService.EVENT)
            intent.putExtra(SyncService.EVENT, action)
            v.context.startService(intent)
        }
    }

    @JvmStatic
    fun onViewTouch(v: View, event: MotionEvent) {
        //FIXME May modify in future
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
            LogUtils.d("monitor", "ViewPath--onViewTouch->" + ViewUtils.getViewPath(v))
            LogUtils.d("monitor", "ViewInfo--onViewTouch->" + ViewUtils.getViewInfo(v))
            ThreadUtils.execute {
                val action = DataFactory.createEventAction(v, EventAction.EVENT_TOUCH)
                val intent = Intent(v.context, SyncService::class.java)
                intent.putExtra(SyncService.TYPE, SyncService.EVENT)
                intent.putExtra(SyncService.EVENT, action)
                v.context.startService(intent)
            }
        }
    }

    @JvmStatic
    fun onViewLongClick(v: View) {
        LogUtils.d("monitor", "ViewPath--onLongClick->" + ViewUtils.getViewPath(v))
        LogUtils.d("monitor", "ViewInfo--onLongClick->" + ViewUtils.getViewInfo(v))
        ThreadUtils.execute {
            val action = DataFactory.createEventAction(v, EventAction.EVENT_LONG_CLICK)
            val intent = Intent(v.context, SyncService::class.java)
            intent.putExtra(SyncService.TYPE, SyncService.EVENT)
            intent.putExtra(SyncService.EVENT, action)
            v.context.startService(intent)
        }
    }

    /**
     * Used for RadioGroup
     */
    @JvmStatic
    fun onCheckChanged(group: RadioGroup, checkedId: Int) {
        var checkedView: View? = null
        for (index in 0..group.childCount) {
            if (group.getChildAt(index).id == checkedId) {
                checkedView = group.getChildAt(index)
                break
            }
        }
        LogUtils.d("monitor", "ViewPath--onCheckChanged->" + ViewUtils.getViewPath(checkedView!!))
        LogUtils.d("monitor", "ViewInfo--onCheckChanged->" + ViewUtils.getViewInfo(checkedView))
        ThreadUtils.execute {
            val action = DataFactory.createEventAction(checkedView, EventAction.EVENT_RADIOGROUP)
            val intent = Intent(checkedView.context, SyncService::class.java)
            intent.putExtra(SyncService.TYPE, SyncService.EVENT)
            intent.putExtra(SyncService.EVENT, action)
            checkedView.context.startService(intent)
        }
    }

    /**
     * Used for CompoundButton
     */
    @JvmStatic
    fun onCheckChanged(buttonView: CompoundButton, isChecked: Boolean) {
        //FIXME May modify in future
        if (!isChecked) return
        LogUtils.d("monitor", "ViewPath--onCheckChanged->" + ViewUtils.getViewPath(buttonView))
        LogUtils.d("monitor", "ViewInfo--onCheckChanged->" + ViewUtils.getViewInfo(buttonView))
        ThreadUtils.execute {
            val action = DataFactory.createEventAction(buttonView, EventAction.EVENT_COMPOUNDBUTTON)
            val intent = Intent(buttonView.context, SyncService::class.java)
            intent.putExtra(SyncService.TYPE, SyncService.EVENT)
            intent.putExtra(SyncService.EVENT, action)
            buttonView.context.startService(intent)
        }
    }
}
