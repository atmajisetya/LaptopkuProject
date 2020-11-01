package com.example.laptopku

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LaptopTerbaru(
    var name: String = "",
    var price: String = "",
    var photo: String = ""
):Parcelable