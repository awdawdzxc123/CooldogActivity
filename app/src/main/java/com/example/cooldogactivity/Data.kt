package com.example.cooldogactivity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Data(
        var geming:String,var geshou:String,var tupian:Int,var musicId:String):
    Parcelable {
}