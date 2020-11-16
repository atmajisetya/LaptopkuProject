package com.example.laptopku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //inisiasi RecyclerView yang akan ditampilkan untuk rilis terbaru
    private lateinit var rvLaptop: RecyclerView

    //untuk laptop terbaru
    private val listTerbaru: ArrayList<LaptopTerbaru> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ini juga inisiasi untuk laptop terbaru
        rvLaptop = findViewById(R.id.rv_laptop)
        rvLaptop.setHasFixedSize(true)

        //menampilkan progress bar
        rilisTerbaruProgressBar.visibility = View.VISIBLE

        //memanggil data yang ada di firebase bar kui dipancal (ditampilke)
        loadLaptopTerbaru()

        //digunakan untuk pindah ke tampilan hasil telusuri
        val gaming: ImageView = findViewById(R.id.gamingImageView)
        gaming.setOnClickListener(this)

        val profesional: ImageView = findViewById(R.id.profesionalImageView)
        profesional.setOnClickListener(this)

        val pelajar: ImageView = findViewById(R.id.pelajarImageView)
        pelajar.setOnClickListener(this)

        val workstation: ImageView = findViewById(R.id.workstationImageView)
        workstation.setOnClickListener(this)

        val acer: ImageView = findViewById(R.id.acerImageView)
        acer.setOnClickListener(this)

        val asus: ImageView = findViewById(R.id.asusImageView)
        asus.setOnClickListener(this)

        val hp: ImageView = findViewById(R.id.hpImageView)
        hp.setOnClickListener(this)

        val lenovo: ImageView = findViewById(R.id.lenovoImageView)
        lenovo.setOnClickListener(this)

        val msi: ImageView = findViewById(R.id.msiImageView)
        msi.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan rekomendasi
        val imgMenuRekomendasi: ImageView = findViewById(R.id.telusuriFooterRekomendasiImageView)
        imgMenuRekomendasi.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan bandingkan
        val imgMenuBandingkan: ImageView = findViewById(R.id.telusuriFooterBandingkanImageView)
        imgMenuBandingkan.setOnClickListener(this)

        //digunakan untuk pindah ke tampilan favorit
        val favoriteImageView: ImageView = findViewById(R.id.mainActivityFavoriteImageView)
        favoriteImageView.setOnClickListener(this)
    }

    //fungsi untuk mengambil data dari database firestore
    private fun loadLaptopTerbaru(){
        // listTerbaru.clear()
        val db = FirebaseFirestore.getInstance()
        db.collection("spekLaptop")
            .orderBy("tanggalRilis", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {result ->
                for (document in result){
                    listTerbaru.add(LaptopTerbaru(document.getString("namaLaptop")!!,
                        document.getString("hargaLaptop")!!,
                        document.getString("gambar")!!))
                }
                if(listTerbaru.isNotEmpty()){
                    showRecyclerList()
                    rilisTerbaruProgressBar.visibility = View.GONE
                }
                else
                    loadLaptopTerbaru()
            }
    }
    //untuk menampilkan RecyclerView Laptop Terbaru
    private fun showRecyclerList(){
        rvLaptop.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listLaptopTerbaruAdapter = ListLaptopTerbaruAdapter(applicationContext, listTerbaru)
        rvLaptop.adapter = listLaptopTerbaruAdapter
    }

    //fungsi untuk pindah tampilan ke telusuri rekomendasi bandingkan
    override fun onClick(v: View?){
        when(v?.id){
            R.id.gamingImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.pelajarImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.profesionalImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.workstationImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.acerImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.asusImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.hpImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.lenovoImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.msiImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.telusuriFooterRekomendasiImageView ->{
                val moveIntent = Intent(this@MainActivity, RekomendasiActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.telusuriFooterBandingkanImageView ->{
                val moveIntent = Intent(this@MainActivity, BandingkanActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.mainActivityFavoriteImageView ->{
                val moveIntent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(moveIntent)
            }
        }
    }
}

