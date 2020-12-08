package com.warnet.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.warnet.laptopku.*
import kotlinx.android.synthetic.main.activity_bandingkan.*
import kotlinx.android.synthetic.main.activity_deskripsi_laptop.*

class BandingkanActivity : AppCompatActivity(), View.OnClickListener {
    // Variabel untuk menerima operan dari Activity Favorite atau Acitivy Deskripsi Laptop (bila ada operan)
    private var laptopKiri: LaptopTerbaru? = null
    private lateinit var laptopKanan: LaptopTerbaru

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bandingkan)

        // Memunculkan nama laptopFavorite ke EditText cari laptop yang kiri
        laptopKiri = intent.getParcelableExtra("laptopKiri")
        if(laptopKiri != null){
            cariLaptopKiriEditText.setText(laptopKiri!!.name)
            muatLaptopKiri()
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

    fun muatLaptopKiri(){
// Mengisi ImageView dengan foto laptop
        Glide.with(applicationContext)
            .load(laptopKiri?.photo)
            .apply(
                RequestOptions().fitCenter().format(DecodeFormat.PREFER_ARGB_8888).override(
                    Target.SIZE_ORIGINAL))
            .into(laptopKiriImageView)

        // Menampilkan nama dan harga laptop
        namaLaptopKiriTextView.text = laptopKiri?.name
        hargaLaptopKiriTextView.text = laptopKiri?.price

        // Menampilkan spesifikasi-spesifikasi tunggal laptop
        acAdapterKiriTextView.text = laptopKiri?.acadapter
        audioKiriTextView.text = laptopKiri?.audio
        bateraiKiriTextView.text = laptopKiri?.baterai
        cpuKiriTextView.text = laptopKiri?.cpu
        osKiriTextView.text = laptopKiri?.os
        layarKiriTextView.text = laptopKiri?.layar
        chipsetKiriTextView.text = laptopKiri?.chipset
        memoriKiriTextView.text = laptopKiri?.memori
        penyimpananKiriTextView.text = laptopKiri?.penyimpanan
        webcamKiriTextView.text = laptopKiri?.webcam
        keyboardKiriTextView.text = laptopKiri?.keyboard
        dimensiKiriTextView.text = laptopKiri?.dimensi
        beratKiriTextView.text = laptopKiri?.berat

        // Menampilkan spesifikasi-spesifikasi ganda laptop
        for(i in 0 until laptopKiri?.grafis!!.size){
            if(i == 0){
                grafisKiriTextView.text = laptopKiri!!.grafis[i]
            }
            else{
                grafisKiriTextView.append("\n" + laptopKiri!!.grafis[i])
            }
        }
        for(i in 0 until laptopKiri?.io!!.size){
            if(i == 0){
                ioPortsKiriTextView.text = laptopKiri!!.io[i]
            }
            else{
                ioPortsKiriTextView.append("\n" + laptopKiri!!.io[i])
            }
        }
        for(i in 0 until laptopKiri?.komunikasi!!.size){
            if(i == 0){
                komunikasiKiriTextView.text = laptopKiri!!.komunikasi[i]
            }
            else{
                komunikasiKiriTextView.append("\n" + laptopKiri!!.komunikasi[i])
            }
        }
    }
}