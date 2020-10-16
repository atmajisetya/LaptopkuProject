package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    //inisiasi RecyclerView yang akan dipanggil/ditampilkan
    private lateinit var rvLaptop: RecyclerView

    private val list: ArrayList<LaptopTerbaru> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ini juga inisiasi
        rvLaptop = findViewById(R.id.rv_laptop)
        rvLaptop.setHasFixedSize(true)

        //manggil data yang ada di kelas LaptopTerbaruData
        list.addAll(LaptopTerbaruData.listData)
        showRecyclerList()

    }
    private fun showRecyclerList(){
        rvLaptop.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listLaptopTerbaruAdapter = ListLaptopTerbaruAdapter(list)
        rvLaptop.adapter = listLaptopTerbaruAdapter
    }
}