package  com.plugin.monitor.db.model

import android.os.Parcel
import android.os.Parcelable

internal class PageAction() : Parcelable {
    var pageStartTime: Long = 0L
    var pageEndTime: Long = 0L
    var pageId: String? = null
    var referPageId: String? = null
    var duration: Long = 0L
    var pageName: CharSequence? = null
    var referPageName: CharSequence? = null

    constructor(parcel: Parcel) : this() {
        pageStartTime = parcel.readLong()
        pageEndTime = parcel.readLong()
        pageId = parcel.readString()
        referPageId = parcel.readString()
        duration = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(pageStartTime)
        parcel.writeLong(pageEndTime)
        parcel.writeString(pageId)
        parcel.writeString(referPageId)
        parcel.writeLong(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PageAction> {
        override fun createFromParcel(parcel: Parcel): PageAction {
            return PageAction(parcel)
        }

        override fun newArray(size: Int): Array<PageAction?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "PageAction(${pageId?.substring(pageId!!.lastIndexOf(".") + 1,
                pageId!!.length)}($pageName), ${duration / 1000f}秒)"
    }
}