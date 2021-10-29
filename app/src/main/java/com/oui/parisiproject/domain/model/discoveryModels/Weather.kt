package com.oui.parisiproject.domain.model.discoveryModels

import android.os.Parcel
import android.os.Parcelable

data class Weather(
    val date: String,
    val hour: String,
    val icon: String,
    val temperature: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.readString().toString()
        p0?.readString().toString()
        p0?.readString().toString()
        p0?.readInt()
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}
