package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class RekomendasiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var transaction: FragmentTransaction
    private lateinit var selanjutnyaButton: android.widget.LinearLayout
    private lateinit var sebelumnyaButton: android.widget.LinearLayout
    private var currentFragment = "budget"

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

        //digunakan untuk pindah ke fragment selanjutnya
        selanjutnyaButton = findViewById(R.id.selanjutnyaButton)
        selanjutnyaButton.setOnClickListener(this)

        //digunakan untuk kembali ke tampilan atau fragment sebelumnya
        sebelumnyaButton = findViewById(R.id.sebelumnyaButton)
        sebelumnyaButton.setOnClickListener(this)
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
            R.id.selanjutnyaButton ->{
                if (currentFragment == "budget"){
                    transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.rekomendasiFrameLayout, KeperluanFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                    currentFragment = "keperluan"
                    sebelumnyaButton.setBackgroundResource(R.drawable.bg_button_biru)
                }
                else if (currentFragment == "keperluan"){
                    transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.rekomendasiFrameLayout, PrioritasFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                    currentFragment = "prioritas"
                }
                else if (currentFragment == "prioritas"){
                    transaction = fragmentManager.beginTransaction()
                    transaction.replace(R.id.rekomendasiFrameLayout, BrandFragment())
                    transaction.addToBackStack(null)
                    transaction.commit()
                    currentFragment = "brand"
                }
                else if (currentFragment == "brand"){

                }
            }
            R.id.rekomendasiKembaliImageView, R.id.sebelumnyaButton -> {
                if (currentFragment == "budget")
                    finish()
                else {
                    fragmentManager.popBackStack()
                    if (currentFragment == "keperluan"){
                        currentFragment = "budget"
                        sebelumnyaButton.setBackgroundResource(R.drawable.bg_button_abu)
                    }
                    else if (currentFragment == "prioritas")
                        currentFragment = "keperluan"
                    else if (currentFragment == "brand")
                        currentFragment = "prioritas"
                }
            }
        }
    }
}