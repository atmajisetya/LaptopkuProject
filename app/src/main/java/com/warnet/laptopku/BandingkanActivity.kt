package com.warnet.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.warnet.laptopku.*
import kotlinx.android.synthetic.main.activity_bandingkan.*

class BandingkanActivity : AppCompatActivity(), View.OnClickListener {
    // Variabel untuk menerima operan dari Activity Favorite atau Acitivy Deskripsi Laptop (bila ada operan)
    private var laptopKiri: LaptopTerbaru? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bandingkan)

        // Memunculkan nama laptopFavorite ke EditText cari laptop yang kiri
        laptopKiri = intent.getParcelableExtra("laptopKiri")
        if(laptopKiri != null){
            cariLaptopKiriEditText.setText(laptopKiri!!.name)
        }

        // Mendaftarkan event klik untuk pindah ke Activity Telusuri
        val telusuriImageView: android.widget.ImageView = findViewById(R.id.bandingkanFooterTelusuriImageView)
        telusuriImageView.setOnClickListener(this)

        // Mendaftarkan event klik untuk pindah ke Activity Rekomendasi
        val rekomendasiImageView: android.widget.ImageView = findViewById(R.id.bandingkanFooterRekomendasiImageView)
        rekomendasiImageView.setOnClickListener(this)

        // Mendaftarkan event klik untuk pindah ke Activity Favorit
        val favoriteImageView: android.widget.ImageView = findViewById(R.id.bandingkanFavoriteImageView)
        favoriteImageView.setOnClickListener(this)

        // Mendaftarkan event klik untuk kembali ke activity sebelumnya
        val kembaliImageView: android.widget.ImageView = findViewById(R.id.bandingkanKembaliImageView)
        kembaliImageView.setOnClickListener(this)
    }

    // Semua event klik
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bandingkanFooterTelusuriImageView ->{
                val moveIntent = android.content.Intent(this@BandingkanActivity, MainActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.bandingkanFooterRekomendasiImageView ->{
                val moveIntent = android.content.Intent(this@BandingkanActivity, RekomendasiActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.bandingkanFavoriteImageView ->{
                val moveIntent = android.content.Intent(this@BandingkanActivity, FavoriteActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.bandingkanKembaliImageView -> finish()
        }
    }
}