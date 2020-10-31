package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity(), View.OnClickListener {
    //inisiasi RecyclerView yang akan ditampilkan untuk laptop favorit
    private lateinit var rvFavorite: RecyclerView

    //untuk laptop favorit
    private val list: ArrayList<LaptopFavorite> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        //ini juga inisiasi untuk laptop favorit
        rvFavorite = findViewById(R.id.rv_favorite)
        rvFavorite.setHasFixedSize(true)

        //memanggil data yang ada di kelas LaptopFavoriteData
        list.addAll(LaptopFavoriteData.listData)
        showRecyclerList()

        //digunakan untuk pindah ke tampilan telusuri
        val telusuriImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterTelusuriImageView)
        telusuriImageView.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan rekomendasi
        val rekomendasiImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterRekomendasiImageView)
        rekomendasiImageView.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan bandingkan
        val bandingkanImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterBandingkanImageView)
        bandingkanImageView.setOnClickListener(this)

        //digunakan untuk kembali ke tampilan sebelumnya
        val kembaliImageView: android.widget.ImageView = findViewById(R.id.favoriteKembaliImageView)
        kembaliImageView.setOnClickListener(this)
    }

    //untuk menampilkan RecyclerView Laptop Favorit
    private fun showRecyclerList(){
        rvFavorite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val listLaptopFavoriteAdapter = ListLaptopFavoriteAdapter(list)
        rvFavorite.adapter = listLaptopFavoriteAdapter
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.telusuriFooterTelusuriImageView ->{
                val moveIntent = android.content.Intent(this@FavoriteActivity, MainActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.telusuriFooterRekomendasiImageView ->{
                val moveIntent = android.content.Intent(this@FavoriteActivity, RekomendasiActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.telusuriFooterBandingkanImageView ->{
                val moveIntent = android.content.Intent(this@FavoriteActivity, BandingkanActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.favoriteKembaliImageView -> finish()
        }
    }
}