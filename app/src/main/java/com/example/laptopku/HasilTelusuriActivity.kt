package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HasilTelusuriActivity : AppCompatActivity(), View.OnClickListener {
    //inisiasi TextView
    private lateinit var textView: android.widget.TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hasil_telusuri)

        //ini juga inisiasi untuk TextView
        textView = findViewById(R.id.urutkanTextView)
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_urutkan,0,0,0)
        textView.compoundDrawablePadding = 32
        textView = findViewById(R.id.filterTextView)
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_filter,0,0,0)
        textView.compoundDrawablePadding = 32

        //digunakan untuk pindah ke tampilan rekomendasi
        val rekomendasiImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterRekomendasiImageView)
        rekomendasiImageView.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan bandingkan
        val bandingkanImageView: android.widget.ImageView = findViewById(R.id.telusuriFooterBandingkanImageView)
        bandingkanImageView.setOnClickListener(this)

        //digunakan untuk kembali ke tampilan sebelumnya
        val kembaliImageView: android.widget.ImageView = findViewById(R.id.hasilTelusuriKembaliImageView)
        kembaliImageView.setOnClickListener(this)
    }

    //fungsi untuk pindah ke tampilan rekomendasi dan bandingkan
    override fun onClick(v: View?){
        when(v?.id){
            R.id.telusuriFooterRekomendasiImageView ->{
                val moveIntent = android.content.Intent(this@HasilTelusuriActivity, RekomendasiActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.telusuriFooterBandingkanImageView ->{
                val moveIntent = android.content.Intent(this@HasilTelusuriActivity, BandingkanActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.hasilTelusuriKembaliImageView -> finish()
        }
    }
}