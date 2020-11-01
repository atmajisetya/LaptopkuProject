package com.example.laptopku

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SpekLaptop(
    var namaLaptop: String="",
    var hargaLaptop: String="",
    var cpu: String="",
    var os: String="",
    var layar: String="",
    var chipset: String="",
    /*
    //iki mbuh digae array ra
    var grafis: String="",
    var penyimpanan: String="",
    var webcam: String="",
    var keyboard: String="",
    //iki mbuh digae array ra
    var komunikasi: String="",
    var audio: String="",
    //iki mbuh digae array ra
    var ioPorts: String="",
    var baterai: String="",
    var acAdapter: String="",
    var dimensi: String="",
    var berat: String=""

     */
):Parcelable