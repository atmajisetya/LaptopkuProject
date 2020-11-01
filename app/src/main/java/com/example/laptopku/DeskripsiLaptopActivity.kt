package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class DeskripsiLaptopActivity : AppCompatActivity() {
    //inisiasi RecyclerView yang akan ditampilkan untuk spesifikasi laptop
    private lateinit var rvSpekLaptop: RecyclerView
    //untuk spesifikasi laptop
    private val listSpekLaptop: ArrayList<SpekLaptop> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deskripsi_laptop)

        //inisiasi untuk spek laptop
        rvSpekLaptop= findViewById(R.id.rv_spek_laptop)
        rvSpekLaptop.setHasFixedSize(true)
    }
}