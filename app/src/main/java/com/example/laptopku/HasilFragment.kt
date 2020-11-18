package com.example.laptopku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Timestamp

// Konstruktor primer dipanggil ketika ingin menampilkan semua laptop
class HasilFragment() : Fragment() {
    // Inisiasi variabel agar Fragment mengetahui laptop bagaimana yang diminta
    private var extraType = "none"
    private var extra = "all"

    // Inisiasi variabel untuk menampilkan icon urutkan dan filter
    private lateinit var textView: android.widget.TextView

    // Inisiasi variabel RecyclerView yang akan menampilkan grid laptop
    private lateinit var hasilRecyclerView: androidx.recyclerview.widget.RecyclerView

    // Inisiasi variabel ProgressBar
    private lateinit var progressBar: android.widget.ProgressBar

    // Inisiasi ArrayList untuk menampung data laptop yang diminta
    private val listLaptop: ArrayList<LaptopTerbaru> = arrayListOf()

    // Konstruktor sekunder dipanggil ketika pengguna meminta brand atau kategori tertentu
    constructor(extraType: String, extra: String) : this(){
        this.extraType = extraType
        this.extra = extra
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_hasil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Menampilkan icon urutkan dan filter
        textView = view.findViewById(R.id.urutkanTextView)
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_urutkan,0,0,0)
        textView.compoundDrawablePadding = 32
        textView = view.findViewById(R.id.filterTextView)
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_filter,0,0,0)
        textView.compoundDrawablePadding = 32

        // Menghubungkan variabel RecyclerView dengan RecyclerView sesungguhnya
        hasilRecyclerView = view.findViewById(R.id.hasilRecyclerView)
        hasilRecyclerView.setHasFixedSize(true)

        // Membuat progress bar menjadi nampak
        progressBar = view.findViewById(R.id.hasilProgressBar)
        progressBar.visibility = View.VISIBLE

        // Memanggil data yang diminta dari Firestore sekaligus ditampilkan
        when (extraType) {
            "none" -> loadLaptopSemua()
            "brand" -> loadLaptopBrand()
            "kategori" -> loadLaptopKategori()
        }
    }

    // Memanggil data semua laptop dari Firestore sekaligus ditampilkan
    private fun loadLaptopSemua(){
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
                        document.getString("webcam")!!
                    ))
                }
                if(listLaptop.isNotEmpty()){
                    showRecyclerList()
                    progressBar.visibility = View.GONE
                }
                else
                    loadLaptopSemua()
            }
    }

    // Memanggil data laptop dengan brand tertentu dari Firestore sekaligus ditampilkan
    private fun loadLaptopBrand(){
        val db = FirebaseFirestore.getInstance()
        db.collection("spekLaptop")
            .whereEqualTo("brand", extra)
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
                        document.getString("webcam")!!))
                }
                if(listLaptop.isNotEmpty()){
                    showRecyclerList()
                    progressBar.visibility = View.GONE
                }
                else
                    loadLaptopBrand()
            }
    }

    // Memanggil data laptop dengan brand tertentu dari Firestore sekaligus ditampilkan
    private fun loadLaptopKategori(){
        val db = FirebaseFirestore.getInstance()
        db.collection("spekLaptop")
            .whereArrayContains("kategori", extra)
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
                        document.getString("webcam")!!))
                }
                if(listLaptop.isNotEmpty()){
                    showRecyclerList()
                    progressBar.visibility = View.GONE
                }
                else
                    loadLaptopKategori()
            }
    }

    // Menampilkan laptop-laptop yang diminta pada RecyclerView
    private fun showRecyclerList(){
        hasilRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        val listLaptopTerbaruAdapter = ListLaptopTerbaruAdapter(activity, listLaptop)
        hasilRecyclerView.adapter = listLaptopTerbaruAdapter
    }
}