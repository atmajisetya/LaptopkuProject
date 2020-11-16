package com.example.laptopku

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LaptopFavorite(
    var name: String = "",
    var photo: String = ""
):Parcelable