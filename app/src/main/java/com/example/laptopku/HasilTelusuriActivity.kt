package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class HasilTelusuriActivity : AppCompatActivity(), View.OnClickListener {
    // Inisiasi variabel brand dan kategori untuk menerima operan dari Main Activity (bila ada)
    private var brand: String? = null
    private var kategori: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_telusuri)

        // Memanggil Fragment Hasil
        // Assignment variabel operan
        brand = intent.getStringExtra("brand")
        kategori = intent.getStringExtra("kategori")
        val transaction = supportFragmentManager.beginTransaction()
        when {
            // Jika Activity dipanggil dari ImageView Kategori: menampilkan kategori tertentu
            kategori != null -> transaction.add(R.id.hasilTelusuriFrameLayout, HasilFragment("kategori", kategori!!))
            // Jika Activity dipanggil dari ImageView Brand: menampilkan brand tertentu
            brand != null -> transaction.add(R.id.hasilTelusuriFrameLayout, HasilFragment("brand", brand!!))
            // Jika Activity dipanggil biasa: menampilkan semua laptop
            else -> transaction.add(R.id.hasilTelusuriFrameLayout, HasilFragment())
        }
        transaction.commit()

        // Mendaftarkan event klik untuk pindah Main Activity
        val telusuriImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterTelusuriImageView)
        telusuriImageView.setOnClickListener(this)

        // Mendaftarkan event klik untuk pindah ke Activity Rekomendasi
        val rekomendasiImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterRekomendasiImageView)
        rekomendasiImageView.setOnClickListener(this)

        //  Mendaftarkan event klik untuk pindah ke Activity Bandingkan
        val bandingkanImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterBandingkanImageView)
        bandingkanImageView.setOnClickListener(this)

        //  Mendaftarkan event klik untuk pindah ke Activity Favorit
        val favoriteImageView: android.widget.ImageView = findViewById(R.id.headerFavoriteImageView)
        favoriteImageView.setOnClickListener(this)

        //  Mendaftarkan event klik untuk kembali ke Activity sebelumnya
        val kembaliImageView: android.widget.ImageView = findViewById(R.id.headerKembaliImageView)
        kembaliImageView.setOnClickListener(this)
    }

    // Isi semua event klik
    override fun onClick(v: View?){
        when(v?.id){
            R.id.telusuriFooterTelusuriImageView ->{
                val moveIntent = android.content.Intent(this@HasilTelusuriActivity, MainActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.telusuriFooterRekomendasiImageView ->{
                val moveIntent = android.content.Intent(this@HasilTelusuriActivity, RekomendasiActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.telusuriFooterBandingkanImageView ->{
                val moveIntent = android.content.Intent(this@HasilTelusuriActivity, BandingkanActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.headerFavoriteImageView ->{
                val moveIntent = android.content.Intent(this@HasilTelusuriActivity, FavoriteActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.headerKembaliImageView -> finish()
        }
    }
}