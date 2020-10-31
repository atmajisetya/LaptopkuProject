package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class BandingkanActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bandingkan)

        //digunakan untuk pindah ke tampilan telusuri
        val telusuriImageView: android.widget.ImageView = findViewById(R.id.bandingkanFooterTelusuriImageView)
        telusuriImageView.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan rekomendasi
        val rekomendasiImageView: android.widget.ImageView = findViewById(R.id.bandingkanFooterRekomendasiImageView)
        rekomendasiImageView.setOnClickListener(this)

        //digunakan untuk kembali ke tampilan sebelumnya
        val kembaliImageView: android.widget.ImageView = findViewById(R.id.bandingkanKembaliImageView)
        kembaliImageView.setOnClickListener(this)
    }

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
            R.id.bandingkanKembaliImageView -> finish()
        }
    }
}