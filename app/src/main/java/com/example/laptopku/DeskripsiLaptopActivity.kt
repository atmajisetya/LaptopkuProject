package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_deskripsi_laptop.*

class DeskripsiLaptopActivity : AppCompatActivity() {
    // Variabel untuk menerima operan data spesifikasi laptop dari Activity sebelumnya
    private var laptopTerbaru : LaptopTerbaru? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deskripsi_laptop)

        // Assignment variabel laptopTerbaru
        laptopTerbaru = intent.getParcelableExtra("laptopTerbaru")

        // Mengisi ImageView dengan foto laptop
        Glide.with(applicationContext)
            .load(laptopTerbaru?.photo)
            .apply(
                RequestOptions().fitCenter().format(DecodeFormat.PREFER_ARGB_8888).override(
                    Target.SIZE_ORIGINAL))
            .into(img_laptop)

        // Menampilkan nama dan harga laptop
        val tvName: TextView = findViewById(R.id.tv_namaLaptop)
        val tvHarga: TextView = findViewById(R.id.tv_hargaLaptop)
        tvName.text = laptopTerbaru?.name
        tvHarga.text = laptopTerbaru?.price

        // Menampilkan spesifikasi-spesifikasi tunggal laptop
        tv_acAdapter.text = laptopTerbaru?.acadapter
        tv_audio.text = laptopTerbaru?.audio
        tv_baterai.text = laptopTerbaru?.baterai
        tv_cpu.text = laptopTerbaru?.cpu
        tv_os.text = laptopTerbaru?.os
        tv_layar.text = laptopTerbaru?.layar
        tv_chipset.text = laptopTerbaru?.chipset
        tv_memori.text = laptopTerbaru?.memori
        tv_penyimpanan.text = laptopTerbaru?.penyimpanan
        tv_webcam.text = laptopTerbaru?.webcam
        tv_keyboard.text = laptopTerbaru?.keyboard
        tv_dimensi.text = laptopTerbaru?.dimensi
        tv_berat.text = laptopTerbaru?.berat

        // Menampilkan spesifikasi-spesifikasi ganda laptop
        for(i in 0 until laptopTerbaru?.grafis!!.size){
            if(i == 0){
                tv_grafis.text = laptopTerbaru!!.grafis[i]
            }
            else{
                tv_grafis.append(laptopTerbaru!!.grafis[i])
            }
        }
        for(i in 0 until laptopTerbaru?.io!!.size){
            if(i == 0){
                tv_io.text = laptopTerbaru!!.io[i]
            }
            else{
                tv_io.append(laptopTerbaru!!.io[i])
            }
        }
        for(i in 0 until laptopTerbaru?.komunikasi!!.size){
            if(i == 0){
                tv_komunikasi.text = laptopTerbaru!!.komunikasi[i]
            }
            else{
                tv_komunikasi.append(laptopTerbaru!!.komunikasi[i])
            }
        }
    }
}