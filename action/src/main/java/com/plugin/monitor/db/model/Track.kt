package  com.plugin.monitor.db.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by cool on 2018/3/7.
 */

internal class Track() : Parcelable {
    var startPageId: String? = null
    var currentPageId: String? = null //mark activity
    var actions: ArrayList<PageAction> = ArrayList()

    constructor(parcel: Parcel) : this() {
        startPageId = parcel.readString()
        currentPageId = parcel.readString()
    }

    fun isFinishedTrack(pageId: String): Boolean {
        //FIXME 有可能存在同一个Activity被反复调用(复用)问题
        return startPageId == pageId && actions.size > 1
    }

    fun getPrePage(): PageAction? {
        return if (actions.isEmpty()) {
            null
        } else {
            actions.last()
        }
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

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("=================轨迹开始=============>>\n")
                .append("开始页：$startPageId -> 结束页：$currentPageId\n")
                .append("路径：")
        actions.forEach {
            builder.append("$it --> ")
        }
        builder.append("结束\n")
        builder.append("==============轨迹结束================>>")
        return builder.toString()
    }
}
