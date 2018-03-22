package  com.heshidai.plugin.monitor.db.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by cool on 2018/3/7.
 */

class Track() : Parcelable {
    var startPageId: String? = null
    var currentPageId: String? = null
    var actions: ArrayList<PageAction> = ArrayList()

    constructor(parcel: Parcel) : this() {
        startPageId = parcel.readString()
        currentPageId = parcel.readString()
    }

    fun isFinishedTrack(pageId: String): Boolean {
        return startPageId == pageId && actions.size > 1
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(startPageId)
        parcel.writeString(currentPageId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Track> {
        override fun createFromParcel(parcel: Parcel): Track {
            return Track(parcel)
        }

        override fun newArray(size: Int): Array<Track?> {
            return arrayOfNulls(size)
        }
    }
}
