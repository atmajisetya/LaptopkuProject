package com.example.laptopku

object BrandData {
    private val brandNames = arrayOf("accer",
    "asus", "hp", "lenovo", "msi")

    private val brandImages = intArrayOf(R.drawable.ic_logo_accer,
    R.drawable.ic_logo_asus, R.drawable.ic_logo_hp,
    R.drawable.ic_logo_lenovo, R.drawable.ic_logo_msi)

    val listData: ArrayList<Brand>
        get(){
            val list = arrayListOf<Brand>()
            for (position in brandNames.indices){
                val brand = Brand()
                brand.name = brandNames[position]
                brand.photo = brandImages[position]
                list.add(brand)
            }
            return list
        }
}