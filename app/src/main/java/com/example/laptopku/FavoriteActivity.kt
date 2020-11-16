package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class FavoriteActivity : AppCompatActivity(), View.OnClickListener {
    //inisiasi RecyclerView yang akan ditampilkan untuk laptop favorit
    private lateinit var rvFavorite: RecyclerView

    //untuk laptop favorit
    private val listFavorite: ArrayList<LaptopFavorite> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        //ini juga inisiasi untuk laptop favorit
        rvFavorite = findViewById(R.id.rv_favorite)
        rvFavorite.setHasFixedSize(true)

        //memanggil data yang ada di firebase bar kui dipancal (ditampilke)
        loadLaptopFavorite()

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

    //fungsi untuk mengambil data dari database firestore
    private fun loadLaptopFavorite(){
        // listTerbaru.clear()
        val db = FirebaseFirestore.getInstance()
        db.collection("spekLaptop")
            .orderBy("tanggalRilis", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {result ->
                for (document in result){
                    listFavorite.add(LaptopFavorite(document.getString("namaLaptop")!!,
                        document.getString("gambar")!!))
                }
                if(listFavorite.isNotEmpty()){
                    showRecyclerList()
                    // rilisTerbaruProgressBar.visibility = View.GONE
                }
                else
                    loadLaptopFavorite()
            }
    }

    //untuk menampilkan RecyclerView Laptop Favorit
    private fun showRecyclerList(){
        rvFavorite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val listLaptopFavoriteAdapter = ListLaptopFavoriteAdapter(listFavorite)
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