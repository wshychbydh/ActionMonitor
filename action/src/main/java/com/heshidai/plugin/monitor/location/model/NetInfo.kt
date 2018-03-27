package com.heshidai.plugin.monitor.location.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by cool on 2018/3/27.
 */
class NetInfo() : Parcelable {
    var cip: String? = null
    var cid: String? = null
    var cname: String? = null

    constructor(parcel: Parcel) : this() {
        cip = parcel.readString()
        cid = parcel.readString()
        cname = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cip)
        parcel.writeString(cid)
        parcel.writeString(cname)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NetInfo> {
        override fun createFromParcel(parcel: Parcel): NetInfo {
            return NetInfo(parcel)
        }

        override fun newArray(size: Int): Array<NetInfo?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "NetInfo(cip=$cip, cid=$cid, cname=$cname)"
    }
}