package com.example.laptopku

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Parcelize
data class LaptopTerbaru(
    var name: String = "",
    var price: String = "",
    var photo: String = "",
    var acadapter: String = "",
    var audio: String="",
    var baterai: String="",
    var berat: String="",
    var brand: String="",
    var chipset: String="",
    var cpu: String="",
    var dimensi: String="",
    var grafis: ArrayList<String>,
    var io: ArrayList<String>,
    var kategori: ArrayList<String>,
    var keyboard: String="",
    var komunikasi: ArrayList<String>,
    var layar: String="",
    var memori: String="",
    var os: String="",
    var penyimpanan: String="",
    var tanggalRilis: String="",
    var webcam: String=""

):Parcelable