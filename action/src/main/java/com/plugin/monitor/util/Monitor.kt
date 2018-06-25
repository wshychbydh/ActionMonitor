package com.plugin.monitor.util

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import com.plugin.monitor.db.helper.DataFactory
import com.plugin.monitor.db.model.EventAction

/**
 * Created by cool on 2018/3/7.
 */

object Monitor {

    @JvmStatic
    fun onClick(v: View) {
        ThreadUtils.asyncTask({
            LogUtils.d("monitor", "ViewPath--onClick->" + ViewUtils.getViewPath(v))
            LogUtils.d("monitor", "ViewInfo--onClick->" + ViewUtils.getViewInfo(v))
            DataFactory.createEventAction(v, EventAction.EVENT_CLICK)
        }) {
            ServiceUtil.startServiceWithEvent(v.context, it)
        }
    }

    @JvmStatic
    fun onTouch(v: View, event: MotionEvent) {
        //FIXME May modify in future
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
            ThreadUtils.asyncTask({
                LogUtils.d("monitor", "ViewPath--onTouch->" + ViewUtils.getViewPath(v))
                LogUtils.d("monitor", "ViewInfo--onTouch->" + ViewUtils.getViewInfo(v))
                DataFactory.createEventAction(v, EventAction.EVENT_TOUCH)
            }) {
                ServiceUtil.startServiceWithEvent(v.context, it)
            }
        }
    }

    @JvmStatic
    fun onTouchEvent(v: View, event: MotionEvent) {
        //FIXME May modify in future
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
            ThreadUtils.asyncTask({
                LogUtils.d("monitor", "ViewPath--onTouchEvent->" + ViewUtils.getViewPath(v))
                LogUtils.d("monitor", "ViewInfo--onTouchEvent->" + ViewUtils.getViewInfo(v))
                DataFactory.createEventAction(v, EventAction.EVENT_TOUCH_EVENT)
            }) {
                ServiceUtil.startServiceWithEvent(v.context, it)
            }
        }
    }

    @JvmStatic
    fun onTouchEvent(activity: Activity, event: MotionEvent) {
        //FIXME May modify in future
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_UP) {
            ThreadUtils.asyncTask({
                LogUtils.d("monitor", "ViewPath--onTouchEvent->" + ViewUtils.getActivityPath(activity))
                DataFactory.createEventAction(activity, EventAction.EVENT_TOUCH_EVENT)
            }) {
                ServiceUtil.startServiceWithEvent(activity, it)
            }
        }
    }

    @JvmStatic
    fun onLongClick(v: View) {
        ThreadUtils.asyncTask({
            LogUtils.d("monitor", "ViewPath--onLongClick->" + ViewUtils.getViewPath(v))
            LogUtils.d("monitor", "ViewInfo--onLongClick->" + ViewUtils.getViewInfo(v))
            DataFactory.createEventAction(v, EventAction.EVENT_LONG_CLICK)
        }) {
            ServiceUtil.startServiceWithEvent(v.context, it)
        }
    }

    /**
     * Used for RadioGroup
     */
    @JvmStatic
    fun onCheckedChanged(group: RadioGroup, checkedId: Int) {

        ThreadUtils.asyncTask({
            var checkedView: View? = null
            for (index in 0..group.childCount) {
                if (group.getChildAt(index).id == checkedId) {
                    checkedView = group.getChildAt(index)
                    break
                }
            }
            LogUtils.d("monitor", "ViewPath--onCheckChanged->" + ViewUtils.getViewPath(checkedView!!))
            LogUtils.d("monitor", "ViewInfo--onCheckChanged->" + ViewUtils.getViewInfo(checkedView))
            DataFactory.createEventAction(checkedView, EventAction.EVENT_RADIOGROUP)
        }) {
            ServiceUtil.startServiceWithEvent(group.context, it)
        }
    }

    /**
     * Used for CompoundButton
     */
    @JvmStatic
    fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        //FIXME May modify in future
        if (!isChecked) return
        ThreadUtils.asyncTask({
            LogUtils.d("monitor", "ViewPath--onCheckChanged->" + ViewUtils.getViewPath(buttonView))
            LogUtils.d("monitor", "ViewInfo--onCheckChanged->" + ViewUtils.getViewInfo(buttonView))
            DataFactory.createEventAction(buttonView, EventAction.EVENT_COMPOUNDBUTTON)
        }) {
            ServiceUtil.startServiceWithEvent(buttonView.context, it)
        }
    }
}
