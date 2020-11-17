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
    //inisiasi brand
    private var brand: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_telusuri)

        //memanggil Fragment Hasil
        brand = intent.getStringExtra("brand")
        val transaction = supportFragmentManager.beginTransaction()
        //jika Activity dipanggil dari ImageView brand: menampilkan brand tertentu
        if (brand != null)
            transaction.add(R.id.hasilTelusuriFrameLayout, HasilFragment("brand", brand!!))
        //jika Activity dipanggil biasa: menampilkan semua laptop
        else
            transaction.add(R.id.hasilTelusuriFrameLayout, HasilFragment())
        transaction.commit()

        //digunakan untuk pindah ke home (main activity)
        val telusuriImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterTelusuriImageView)
        telusuriImageView.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan rekomendasi
        val rekomendasiImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterRekomendasiImageView)
        rekomendasiImageView.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan bandingkan
        val bandingkanImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterBandingkanImageView)
        bandingkanImageView.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan favorit
        val favoriteImageView: android.widget.ImageView = findViewById(R.id.hasilTelusuriFavoriteImageView)
        favoriteImageView.setOnClickListener(this)

        //digunakan untuk kembali ke tampilan sebelumnya
        val kembaliImageView: android.widget.ImageView = findViewById(R.id.hasilTelusuriKembaliImageView)
        kembaliImageView.setOnClickListener(this)
    }

    //fungsi untuk pindah ke tampilan rekomendasi dan bandingkan
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