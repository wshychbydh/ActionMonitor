package  com.heshidai.plugin.monitor.db.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.heshidai.plugin.monitor.util.DateUtils

internal class EventAction() : Parcelable {

    @SerializedName("action_time")
    var actionTime: Long = 0L
    @SerializedName("event_name")
    var eventName: String? = null
    @SerializedName("activityName")
    var activityName: String? = null
    @SerializedName("view_path")
    var viewPath: String? = null
    @SerializedName("view_info")
    var viewInfo: String? = null

    constructor(parcel: Parcel) : this() {
        actionTime = parcel.readLong()
        eventName = parcel.readString()
        activityName = parcel.readString()
        viewPath = parcel.readString()
        viewInfo = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(actionTime)
        parcel.writeString(eventName)
        parcel.writeString(activityName)
        parcel.writeString(viewPath)
        parcel.writeString(viewInfo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EventAction> {
        override fun createFromParcel(parcel: Parcel): EventAction {
            return EventAction(parcel)
        }

        override fun newArray(size: Int): Array<EventAction?> {
            return arrayOfNulls(size)
        }

        const val EVENT_CLICK = "onClick"
        const val EVENT_LONG_CLICK = "onLongClick"
        const val EVENT_CHECKED_CHANGED = "onCheckedChanged"
    }

    override fun toString(): String {
        return "EventAction(actionTime=${DateUtils.formatDate(actionTime)}, eventName=$eventName, activityName=$activityName, viewPath=$viewPath, viewInfo=$viewInfo)"
    }
}