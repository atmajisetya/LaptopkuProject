package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    //inisiasi RecyclerView yang akan dipanggil/ditampilkan
    private lateinit var rvLaptop: RecyclerView
    //inisiasi recyclerview yang akan ditampilkan untuk section brand
    private lateinit var rvBrand: RecyclerView

    //untuk laptop terbaru
    private val list: ArrayList<LaptopTerbaru> = arrayListOf()

    //untuk brand
    private val listBrand: ArrayList<Brand> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ini juga inisiasi untuk  laptop terbaru
        rvLaptop = findViewById(R.id.rv_laptop)
        rvLaptop.setHasFixedSize(true)

        //inisiasi untuk brand
        rvBrand = findViewById(R.id.rv_brand)
        rvBrand.setHasFixedSize(true)

        //manggil data yang ada di kelas LaptopTerbaruData
        list.addAll(LaptopTerbaruData.listData)
        showRecyclerList()

        //manggil data yang ada di kelas BrandData
        listBrand.addAll(BrandData.listData)
        showRecyclerGrid()

    }
    //untuk menampilkan Recycler View Laptop Terbaru
    private fun showRecyclerList(){
        rvLaptop.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listLaptopTerbaruAdapter = ListLaptopTerbaruAdapter(list)
        rvLaptop.adapter = listLaptopTerbaruAdapter
    }
    //Untuk menampilkan grid Recycler veiw Brand
    private fun showRecyclerGrid(){
        rvBrand.layoutManager = GridLayoutManager(this,4)
        val gridBrandAdapter = GridBrandAdapter(listBrand)
        rvBrand.adapter = gridBrandAdapter

    }


}