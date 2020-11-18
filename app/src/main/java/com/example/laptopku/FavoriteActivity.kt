package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity(), View.OnClickListener {
    // Inisiasi variabel RecyclerView yang akan menampilkan laptop favorit
    private lateinit var rvFavorite: RecyclerView

    // List untuk menampung laptop favorit
    private val listFavorite: ArrayList<LaptopFavorite> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // Menghubungkan variabel RecyclerView dengan RecyclerView sesungguhnya
        rvFavorite = findViewById(R.id.rv_favorite)
        rvFavorite.setHasFixedSize(true)

        // Membuat progress bar menjadi nampak
        favoriteProgressBar.visibility = View.VISIBLE

        // Memanggil data laptop favorit dari Firestore sekaligus ditampilkan
        loadLaptopFavorite()

        // Mendaftarkan event klik untuk pindah ke Activity Telusuri
        val telusuriImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterTelusuriImageView)
        telusuriImageView.setOnClickListener(this)

        // Mendaftarkan event klik untuk pindah ke Activity Rekomendasi
        val rekomendasiImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterRekomendasiImageView)
        rekomendasiImageView.setOnClickListener(this)

        // Mendaftarkan event klik untuk pindah ke Activity Bandingkan
        val bandingkanImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterBandingkanImageView)
        bandingkanImageView.setOnClickListener(this)

        // Mendaftarkan event klik untuk kembali ke Activity sebelumnya
        val kembaliImageView: android.widget.ImageView = findViewById(R.id.favoriteKembaliImageView)
        kembaliImageView.setOnClickListener(this)
    }

    // Memanggil data laptop favorit dari Firestore sekaligus ditampilkan
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
                    favoriteProgressBar.visibility = View.GONE
                }
                else
                    loadLaptopFavorite()
            }
    }

    // Menampilkan laptop favorit pada RecyclerView
    private fun showRecyclerList(){
        rvFavorite.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val listLaptopFavoriteAdapter = ListLaptopFavoriteAdapter(applicationContext, listFavorite)
        rvFavorite.adapter = listLaptopFavoriteAdapter
    }

    // Semua event klik
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