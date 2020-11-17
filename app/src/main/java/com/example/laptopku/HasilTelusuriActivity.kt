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
    // Inisiasi variabel brand untuk menerima operan dari Main Activity (bila ada)
    private var brand: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_telusuri)

        // Memanggil Fragment Hasil
        brand = intent.getStringExtra("brand")
        val transaction = supportFragmentManager.beginTransaction()
        // Jika Activity dipanggil dari ImageView Brand: menampilkan brand tertentu
        if (brand != null)
            transaction.add(R.id.hasilTelusuriFrameLayout, HasilFragment("brand", brand!!))
        // Jika Activity dipanggil biasa: menampilkan semua laptop
        else
            transaction.add(R.id.hasilTelusuriFrameLayout, HasilFragment())
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
        val favoriteImageView: android.widget.ImageView = findViewById(R.id.hasilTelusuriFavoriteImageView)
        favoriteImageView.setOnClickListener(this)

        //  Mendaftarkan event klik untuk kembali ke Activity sebelumnya
        val kembaliImageView: android.widget.ImageView = findViewById(R.id.hasilTelusuriKembaliImageView)
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
            R.id.hasilTelusuriFavoriteImageView ->{
                val moveIntent = android.content.Intent(this@HasilTelusuriActivity, FavoriteActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.hasilTelusuriKembaliImageView -> finish()
        }
    }
}