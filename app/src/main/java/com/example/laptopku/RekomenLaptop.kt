package com.example.laptopku

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RekomenLaptop(
    var grafis2D: Boolean = false,
    var grafis3D: Boolean = false,
    var brand: String = "",
    var gaming: Boolean = false,
    var harga: Int= 2000000,
    var kalkulasi: Boolean = false,
    var nama: String = "",
    var performa: Int = 1,
    var portabilitas: Int = 1,
    var ringan: Boolean = false,
    var video: Boolean=false,
):Parcelable