package com.plugin.monitor.util

import android.app.Activity
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
    fun onClick(v: View) {
        ThreadUtils.executeOnTrack {
            LogUtils.d("monitor", "ViewPath--onClick->" + ViewUtils.getViewPath(v))
            LogUtils.d("monitor", "ViewInfo--onClick->" + ViewUtils.getViewInfo(v))
            val action = DataFactory.createEventAction(v, EventAction.EVENT_CLICK)
            val intent = Intent(v.context, SyncService::class.java)
            intent.putExtra(SyncService.TYPE, SyncService.EVENT)
            intent.putExtra(SyncService.EVENT, action)
            v.context.startService(intent)
        }
    }

    @JvmStatic
    fun onTouch(v: View, event: MotionEvent) {
        //FIXME May modify in future
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
            LogUtils.d("monitor", "ViewPath--onTouch->" + ViewUtils.getViewPath(v))
            LogUtils.d("monitor", "ViewInfo--onTouch->" + ViewUtils.getViewInfo(v))
            ThreadUtils.executeOnTrack {
                val action = DataFactory.createEventAction(v, EventAction.EVENT_TOUCH)
                val intent = Intent(v.context, SyncService::class.java)
                intent.putExtra(SyncService.TYPE, SyncService.EVENT)
                intent.putExtra(SyncService.EVENT, action)
                v.context.startService(intent)
            }
        }
    }

    @JvmStatic
    fun onTouchEvent(v: View, event: MotionEvent) {
        //FIXME May modify in future
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
            LogUtils.d("monitor", "ViewPath--onTouchEvent->" + ViewUtils.getViewPath(v))
            LogUtils.d("monitor", "ViewInfo--onTouchEvent->" + ViewUtils.getViewInfo(v))
            ThreadUtils.executeOnTrack {
                val action = DataFactory.createEventAction(v, EventAction.EVENT_TOUCH_EVENT)
                val intent = Intent(v.context, SyncService::class.java)
                intent.putExtra(SyncService.TYPE, SyncService.EVENT)
                intent.putExtra(SyncService.EVENT, action)
                v.context.startService(intent)
            }
        }
    }

    @JvmStatic
    fun onTouchEvent(activity: Activity, event: MotionEvent) {
        //FIXME May modify in future
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
            LogUtils.d("monitor", "ViewPath--onTouchEvent->" + ViewUtils.getActivityPath(activity))
            ThreadUtils.executeOnTrack {
                val action = DataFactory.createEventAction(activity, EventAction.EVENT_TOUCH_EVENT)
                val intent = Intent(activity, SyncService::class.java)
                intent.putExtra(SyncService.TYPE, SyncService.EVENT)
                intent.putExtra(SyncService.EVENT, action)
                activity.startService(intent)
            }
        }
    }

    @JvmStatic
    fun onLongClick(v: View) {
        ThreadUtils.executeOnTrack {
            LogUtils.d("monitor", "ViewPath--onLongClick->" + ViewUtils.getViewPath(v))
            LogUtils.d("monitor", "ViewInfo--onLongClick->" + ViewUtils.getViewInfo(v))
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
    fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        ThreadUtils.executeOnTrack {
            var checkedView: View? = null
            for (index in 0..group.childCount) {
                if (group.getChildAt(index).id == checkedId) {
                    checkedView = group.getChildAt(index)
                    break
                }
            }
            LogUtils.d("monitor", "ViewPath--onCheckChanged->" + ViewUtils.getViewPath(checkedView!!))
            LogUtils.d("monitor", "ViewInfo--onCheckChanged->" + ViewUtils.getViewInfo(checkedView))
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
    fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        //FIXME May modify in future
        if (!isChecked) return
        LogUtils.d("monitor", "ViewPath--onCheckChanged->" + ViewUtils.getViewPath(buttonView))
        LogUtils.d("monitor", "ViewInfo--onCheckChanged->" + ViewUtils.getViewInfo(buttonView))
        ThreadUtils.executeOnTrack {
            val action = DataFactory.createEventAction(buttonView, EventAction.EVENT_COMPOUNDBUTTON)
            val intent = Intent(buttonView.context, SyncService::class.java)
            intent.putExtra(SyncService.TYPE, SyncService.EVENT)
            intent.putExtra(SyncService.EVENT, action)
            buttonView.context.startService(intent)
        }
    }
}
