package  com.heshidai.plugin.monitor.db.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class NetworkInfo() : Parcelable {
    @SerializedName("ipAddress")
    var ipAddress: String? = null
    @SerializedName("wifi")
    var wifi: Boolean = false
    @SerializedName("carrier")
    var carrier: String? = null
    @SerializedName("latitude")
    var latitude = 0.0
    @SerializedName("longitude")
    var longitude = 0.0

    constructor(parcel: Parcel) : this() {
        ipAddress = parcel.readString()
        wifi = parcel.readByte() != 0.toByte()
        carrier = parcel.readString()
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ipAddress)
        parcel.writeByte(if (wifi) 1 else 0)
        parcel.writeString(carrier)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NetworkInfo> {
        override fun createFromParcel(parcel: Parcel): NetworkInfo {
            return NetworkInfo(parcel)
        }

        override fun newArray(size: Int): Array<NetworkInfo?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "NetworkInfo(ipAddress=$ipAddress, wifi=$wifi, carrier=$carrier, latitude=$latitude, longitude=$longitude)"
    }
}