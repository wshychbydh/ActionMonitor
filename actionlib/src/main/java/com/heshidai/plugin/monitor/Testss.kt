package com.heshidai.plugin.monitor

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by cool on 2018/3/22.
 */

class Testss protected constructor(`in`: Parcel) : Parcelable {
    private val name: String

    init {
        name = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
    }
}
