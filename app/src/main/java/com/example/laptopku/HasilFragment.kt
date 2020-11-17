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

class HasilFragment() : Fragment() {
    //inisiasi extra
    private var extraType = "none"
    private var extra = "all"

    //inisiasi untuk urutkan dan filter
    private lateinit var textView: android.widget.TextView

    //inisiasi RecyclerView yang akan ditampilkan untuk grid laptop
    private lateinit var hasilRecyclerView: androidx.recyclerview.widget.RecyclerView

    //inisiasi ProgressBar
    private lateinit var progressBar: android.widget.ProgressBar

    //ArrayList untuk grid laptop
    private val listLaptop: ArrayList<LaptopTerbaru> = arrayListOf()

    //Secondary Constructor
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
        //ini juga inisiasi urutkan dan filter
        textView = view.findViewById(R.id.urutkanTextView)
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_urutkan,0,0,0)
        textView.compoundDrawablePadding = 32
        textView = view.findViewById(R.id.filterTextView)
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_filter,0,0,0)
        textView.compoundDrawablePadding = 32

        //ini juga inisiasi untuk RecyclerView
        hasilRecyclerView = view.findViewById(R.id.hasilRecyclerView)
        hasilRecyclerView.setHasFixedSize(true)

        //menampilkan progress bar
        progressBar = view.findViewById(R.id.hasilProgressBar)
        progressBar.visibility = View.VISIBLE

        //memanggil data yang ada di firebase bar kui dipancal (ditampilke)
        loadLaptop()
    }

    //fungsi untuk mengambil data dari database firestore
    private fun loadLaptop(){
        val db = FirebaseFirestore.getInstance()
        if (extraType == "none")
            db.collection("spekLaptop")
                .orderBy("tanggalRilis", Query.Direction.DESCENDING)
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
                            document.getString("kategori")!!,
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
                        loadLaptop()
                }
        else if (extraType == "brand")
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
                            document.getString("kategori")!!,
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
                        loadLaptop()
                }
    }
    //untuk menampilkan RecyclerView Laptop Terbaru
    private fun showRecyclerList(){
        hasilRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        val listLaptopTerbaruAdapter = ListLaptopTerbaruAdapter(activity, listLaptop)
        hasilRecyclerView.adapter = listLaptopTerbaruAdapter
    }
}