package com.example.laptopku

object LaptopFavoriteData {
    private val laptopNames= arrayOf("ASUS TUF FX506II",
        "Lenovo Legion 5","ASUS COBA 1", "ASUS COBA 2", "ASUS COBA 3")

    private val laptopImages = intArrayOf(R.drawable.ic_asustuf,
        R.drawable.ic_lenovolegion,
        R.drawable.ic_asustuf1,
        R.drawable.ic_asustuf2,
        R.drawable.ic_asustuf3)

    val listData: ArrayList<LaptopFavorite>
        get(){
            val list = arrayListOf<LaptopFavorite>()
            for (position in LaptopFavoriteData.laptopNames.indices){
                val laptopFavorite = LaptopFavorite()
                laptopFavorite.name = LaptopFavoriteData.laptopNames[position]
                laptopFavorite.photo = LaptopFavoriteData.laptopImages[position]
                list.add(laptopFavorite)
            }
            return list
        }
}