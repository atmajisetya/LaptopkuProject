package com.example.laptopku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // Inisiasi variabel RecyclerView untuk menampilkan laptop rilis terbaru
    private lateinit var rvLaptop: RecyclerView

    // Inisiasi list untuk menampung data laptop rilis terbaru
    private val listTerbaru: ArrayList<LaptopTerbaru> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Menghubungkan variabel RecyclerView dengan RecyclerView sesungguhnya
        rvLaptop = findViewById(R.id.rv_laptop)
        rvLaptop.setHasFixedSize(true)

        // Membuat progress bar menjadi nampak
        rilisTerbaruProgressBar.visibility = View.VISIBLE

        // Memanggil data laptop rilis terbaru dari Firestore sekaligus ditampilkan
        loadLaptopTerbaru()

        // Membuat event-event EditText Cari Laptop
        cariLaptopEditText.isCursorVisible = false
        cariLaptopEditText.setOnClickListener{
            cariLaptopEditText.isCursorVisible = true
        }
        cariLaptopEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                moveIntent.putExtra("cari", cariLaptopEditText.text.toString())
                startActivity(moveIntent)
                return@OnEditorActionListener true
            }
            false
        })

        // Mendaftarkan event klik untuk pindah ke Activity HasilTelusuri
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

        // Mendaftarkan event klik untuk pindah ke Activity Rekomendasi
        val imgMenuRekomendasi: ImageView = findViewById(R.id.telusuriFooterRekomendasiImageView)
        imgMenuRekomendasi.setOnClickListener(this)

        // Mendaftarkan event klik untuk pindah ke Activity Bandingkan
        val imgMenuBandingkan: ImageView = findViewById(R.id.telusuriFooterBandingkanImageView)
        imgMenuBandingkan.setOnClickListener(this)

        // Mendaftarkan event klik untuk pindah ke Activity Favorit
        val favoriteImageView: ImageView = findViewById(R.id.mainActivityFavoriteImageView)
        favoriteImageView.setOnClickListener(this)
    }

    // Memanggil data laptop rilis terbaru dari Firestore sekaligus ditampilkan
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
                if(listTerbaru.isNotEmpty()){
                    showRecyclerList()
                    rilisTerbaruProgressBar.visibility = View.GONE
                }
                else
                    loadLaptopTerbaru()
            }
    }
    // Menampilkan laptop rilis terbaru pada RecyclerView
    private fun showRecyclerList(){
        rvLaptop.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listLaptopTerbaruAdapter = ListLaptopTerbaruAdapter(applicationContext, listTerbaru)
        rvLaptop.adapter = listLaptopTerbaruAdapter
    }

    // Semua event klik
    override fun onClick(v: View?){
        when(v?.id){
            R.id.gamingImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                moveIntent.putExtra("kategori", "Gaming")
                startActivity(moveIntent)
            }
            R.id.pelajarImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                moveIntent.putExtra("kategori", "Pelajar")
                startActivity(moveIntent)
            }
            R.id.profesionalImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                moveIntent.putExtra("kategori", "Profesional")
                startActivity(moveIntent)
            }
            R.id.workstationImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                moveIntent.putExtra("kategori", "Workstation")
                startActivity(moveIntent)
            }
            R.id.acerImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                moveIntent.putExtra("brand", "Acer")
                startActivity(moveIntent)
            }
            R.id.asusImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                moveIntent.putExtra("brand", "Asus")
                startActivity(moveIntent)
            }
            R.id.hpImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                moveIntent.putExtra("brand", "HP")
                startActivity(moveIntent)
            }
            R.id.lenovoImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                moveIntent.putExtra("brand", "Lenovo")
                startActivity(moveIntent)
            }
            R.id.msiImageView ->{
                val moveIntent = Intent(this@MainActivity, HasilTelusuriActivity::class.java)
                moveIntent.putExtra("brand", "MSI")
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

