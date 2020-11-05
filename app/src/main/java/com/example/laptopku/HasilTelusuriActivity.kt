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
    //inisiasi TextView
    private lateinit var textView: android.widget.TextView

    //inisiasi RecyclerView yang akan ditampilkan untuk grid laptop
    private lateinit var rvTelusuriLaptop: RecyclerView

    //ArrayList untuk grid laptop
    private val listLaptop: ArrayList<LaptopTerbaru> = arrayListOf()

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

        //ini juga inisiasi untuk RecyclerView
        rvTelusuriLaptop = findViewById(R.id.rv_telusuri_laptop)
        rvTelusuriLaptop.setHasFixedSize(true)

        //memanggil data yang ada di firebase bar kui dipancal (ditampilke)
        loadLaptopTerbaru()

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

    //fungsi untuk mengambil data dari database firestore
    private fun loadLaptopTerbaru(){
        // listTerbaru.clear()
        val db = FirebaseFirestore.getInstance()
        db.collection("spekLaptop")
            .orderBy("tanggalRilis", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {result ->
                for (document in result){
                    listLaptop.add(LaptopTerbaru(document.getString("namaLaptop")!!,
                        document.getString("hargaLaptop")!!,
                        document.getString("gambar")!!))
                }
                if(listLaptop.isNotEmpty())
                    showRecyclerList()
                else
                    loadLaptopTerbaru()
            }
    }
    //untuk menampilkan RecyclerView Laptop Terbaru
    private fun showRecyclerList(){
        rvTelusuriLaptop.layoutManager = GridLayoutManager(this, 2)
        val listLaptopTerbaruAdapter = ListLaptopTerbaruAdapter(applicationContext, listLaptop)
        rvTelusuriLaptop.adapter = listLaptopTerbaruAdapter
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