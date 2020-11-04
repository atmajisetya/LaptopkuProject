package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class RekomendasiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var transaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekomendasi)

        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.rekomendasiFrameLayout, BudgetFragment())
        transaction.commit()

        //digunakan untuk pindah ke tampilan telusuri
        val telusuriImageView: android.widget.ImageView = findViewById(R.id.rekomendasiFooterTelusuriImageView)
        telusuriImageView.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan bandingkan
        val bandingkanImageView: android.widget.ImageView = findViewById(R.id.rekomendasiFooterBandingkanImageView)
        bandingkanImageView.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan favorit
        val favoriteImageView: android.widget.ImageView = findViewById(R.id.rekomendasiFavoriteImageView)
        favoriteImageView.setOnClickListener(this)

        //digunakan untuk pindah ke fragment keperluan
        val budgetSelanjutnyaButton: android.widget.LinearLayout? = findViewById(R.id.budgetFragmentSelanjutnyaButton)
        if (budgetSelanjutnyaButton != null) {
            budgetSelanjutnyaButton.setOnClickListener(this)
        }

        //digunakan untuk kembali ke tampilan sebelumnya
        val kembaliImageView: android.widget.ImageView = findViewById(R.id.rekomendasiKembaliImageView)
        kembaliImageView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rekomendasiFooterTelusuriImageView ->{
                val moveIntent = android.content.Intent(this@RekomendasiActivity, MainActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.rekomendasiFooterBandingkanImageView ->{
                val moveIntent = android.content.Intent(this@RekomendasiActivity, BandingkanActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.rekomendasiFavoriteImageView ->{
                val moveIntent = android.content.Intent(this@RekomendasiActivity, FavoriteActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.budgetFragmentSelanjutnyaButton ->{
                transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.rekomendasiFrameLayout, KeperluanFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.rekomendasiKembaliImageView -> finish()
        }
    }
}