package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class RekomendasiActivity : AppCompatActivity(), View.OnClickListener {
    // fragmentManager beserta transaction untuk mengatur pergantian fragment
    private lateinit var fragmentManager: FragmentManager
    private lateinit var transaction: FragmentTransaction

    // Inisiasi variabel untuk mengakses Button Selanjutnya dan Sebelumnya
    private lateinit var selanjutnyaButton: android.widget.LinearLayout
    private lateinit var sebelumnyaButton: android.widget.LinearLayout

    // Inisiasi variabel untuk menyimpan fragment
    val budgetFragment = BudgetFragment()
    private lateinit var keperluanFragment: KeperluanFragment

    // Variabel agar Activity dapat mengenali sedang memuat fragment yang mana
    private var currentFragment = "budget"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekomendasi)

        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.rekomendasiFrameLayout, budgetFragment)
        transaction.commit()

        // Digunakan untuk pindah ke tampilan telusuri
        val telusuriImageView: android.widget.ImageView = findViewById(R.id.rekomendasiFooterTelusuriImageView)
        telusuriImageView.setOnClickListener(this)

        // Digunakan untuk pindah ke tampilan bandingkan
        val bandingkanImageView: android.widget.ImageView = findViewById(R.id.rekomendasiFooterBandingkanImageView)
        bandingkanImageView.setOnClickListener(this)

        // Digunakan untuk pindah ke tampilan favorit
        val favoriteImageView: android.widget.ImageView = findViewById(R.id.rekomendasiFavoriteImageView)
        favoriteImageView.setOnClickListener(this)

        // Digunakan untuk pindah ke fragment selanjutnya
        selanjutnyaButton = findViewById(R.id.selanjutnyaButton)
        selanjutnyaButton.setOnClickListener(this)

        // Digunakan untuk kembali ke tampilan atau fragment sebelumnya
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
                transaction = fragmentManager.beginTransaction()
                when (currentFragment) {
                    "budget" -> {
                        if(budgetFragment.minEditText.text.isNullOrBlank() || budgetFragment.maxEditText.text.isNullOrBlank()){
                            showToast("Budget minimal dan budget maximal tidak boleh kosong.")
                        }
                        else if(budgetFragment.min > budgetFragment.max){
                            showToast("Budget minimal harus lebih kecil dari budget maximal.")
                        }
                        else{
                            keperluanFragment = KeperluanFragment(budgetFragment.min, budgetFragment.max)
                            transaction.replace(R.id.rekomendasiFrameLayout, keperluanFragment)
                            transaction.addToBackStack(null)
                            transaction.commit()
                            currentFragment = "keperluan"
                            sebelumnyaButton.setBackgroundResource(R.drawable.bg_button_biru)
                        }
                    }
                    "keperluan" -> {
                        transaction.replace(R.id.rekomendasiFrameLayout, PrioritasFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                        currentFragment = "prioritas"
                    }
                    "prioritas" -> {
                        transaction.replace(R.id.rekomendasiFrameLayout, BrandFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                        currentFragment = "brand"
                    }
                    "brand" -> {
                        transaction.replace(R.id.rekomendasiFrameLayout, HasilFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                        sebelumnyaButton.visibility = View.GONE
                        selanjutnyaButton.visibility = View.GONE
                        currentFragment = "hasil"
                    }
                }
            }
            R.id.rekomendasiKembaliImageView, R.id.sebelumnyaButton -> {
                if (currentFragment == "budget")
                    finish()
                else {
                    fragmentManager.popBackStack()
                    sesuaikanCurrentFragmentKetikaKembali()
                }
            }
        }
    }

    override fun onBackPressed(){
        super.onBackPressed()
        sesuaikanCurrentFragmentKetikaKembali()
    }

    fun showToast(text: String){
        val toast = android.widget.Toast.makeText(applicationContext,
            text,
            android.widget.Toast.LENGTH_LONG)
        toast.setGravity(android.view.Gravity.BOTTOM,0,130)
        toast.show()
    }

    fun sesuaikanCurrentFragmentKetikaKembali(){
        when (currentFragment) {
            "keperluan" -> {
                currentFragment = "budget"
                sebelumnyaButton.setBackgroundResource(R.drawable.bg_button_abu)
            }
            "prioritas" -> currentFragment = "keperluan"
            "brand" -> currentFragment = "prioritas"
            "hasil" -> {
                sebelumnyaButton.visibility = View.VISIBLE
                selanjutnyaButton.visibility = View.VISIBLE
                currentFragment = "brand"
            }
        }
    }
}