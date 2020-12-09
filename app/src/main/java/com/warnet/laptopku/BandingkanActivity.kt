package com.warnet.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.FirebaseFirestore
import com.warnet.laptopku.*
import kotlinx.android.synthetic.main.activity_bandingkan.*
import kotlinx.android.synthetic.main.activity_deskripsi_laptop.*
import kotlinx.android.synthetic.main.fragment_hasil.*

class BandingkanActivity : AppCompatActivity(), View.OnClickListener {
    // Variabel untuk menyimpan laptop kiri dan laptop kanan
    private var laptopKiri: LaptopTerbaru? = null // Bisa jadi berupa operan dari Acitivity Favorite atau Activity Deskripsi Laptop
    private lateinit var laptopKanan: LaptopTerbaru

    // Variabel untuk menyimpan semua data laptop
    private val listLaptop: ArrayList<LaptopTerbaru> = arrayListOf()
    private val autoComplete: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bandingkan)

        // Menampilkan laptop yang ingin dibandingkan di kiri, jika Activity ini dipanggil dari Activity Favorite atau Activity Deskripsi Laptop
        laptopKiri = intent.getParcelableExtra("laptopKiri")
        if(laptopKiri != null){
            cariLaptopKiriAutoCompleteTextView.setText(laptopKiri!!.name)
            muatLaptopKiri()
        }

        muatSemuaLaptop()

        // Membuat adapter untuk AutoCompleteTextView
        val adapter = android.widget.ArrayAdapter(this, android.R.layout.simple_list_item_1, autoComplete)
        cariLaptopKiriAutoCompleteTextView.setAdapter(adapter)
        cariLaptopKananAutoCompleteTextView.setAdapter(adapter)

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

    // Memanggil data laptop dengan brand tertentu dari Firestore sekaligus ditampilkan
    private fun muatSemuaLaptop(){
        val db = FirebaseFirestore.getInstance()
        db.collection("spekLaptop")
            .get()
            .addOnSuccessListener {result ->
                for (document in result){
                    listLaptop.add(LaptopTerbaru(document.getString("namaLaptop")!!,
                        document.getString("hargaLaptop")!!,
                        document.getString("gambar")!!,
                        document.getString("acadapter")!!,
                        document.getString("audio")!!,
                        document.getString("baterai")!!,
                        document.getString("berat")!!,
                        document.getString("brand")!!,
                        document.getString("chipset")!!,
                        document.getString("cpu")!!,
                        document.getString("dimensi")!!,
                        document.get("grafis")!! as ArrayList<String>,
                        document.get("io")!! as ArrayList<String>,
                        document.get("kategori")!! as ArrayList<String>,
                        document.getString("keyboard")!!,
                        document.get("komunikasi")!! as ArrayList<String>,
                        document.getString("layar")!!,
                        document.getString("memori")!!,
                        document.getString("os")!!,
                        document.getString("penyimpanan")!!,
                        document.getString("tanggalRilis")!!,
                        document.getString("webcam")!!,
                        document.getLong("performa")!!.toInt(),
                        document.getLong("portabilitas")!!.toInt()))
                }
                if(listLaptop.isNotEmpty())
                    listLaptop.forEach{ autoComplete.add(it.name) }
                else
                    muatSemuaLaptop()
            }
    }
}