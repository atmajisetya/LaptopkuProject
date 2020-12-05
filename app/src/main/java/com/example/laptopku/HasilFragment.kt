package com.example.laptopku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_hasil.*

// Konstruktor primer dipanggil ketika ingin menampilkan semua laptop
class HasilFragment() : Fragment() {
    // Inisiasi variabel agar Fragment mengetahui laptop bagaimana yang diminta
    private var extraType = "none"
    private var extra = "all"
    private var min = 0
    private var max = 99999999
    private var gameBerat = true
    private var kalkulasiRumit = true
    private var grafis2D = true
    private var grafis3D = true
    private var editingVideo = true
    private var pekerjaanRingan = true
    private var isPerforma = false
    private var isAcer = true
    private var isAsus = true
    private var isHp = true
    private var isLenovo = true
    private var isMsi = true

    // Inisiasi variabel RecyclerView yang akan menampilkan grid laptop
    private lateinit var hasilRecyclerView: androidx.recyclerview.widget.RecyclerView

    // Inisiasi variabel ProgressBar
    private lateinit var progressBar: android.widget.ProgressBar

    // Inisiasi ArrayList untuk menampung data laptop yang diminta
    private val listLaptop: ArrayList<LaptopTerbaru> = arrayListOf()
    private val rekomenLaptop: ArrayList<RekomenLaptop> = arrayListOf()

    // Boolean untuk mengetahui apakah overlay sedang tampil
    internal var isOverlayUrutkan = false

    // Konstruktor sekunder dipanggil ketika pengguna meminta brand atau kategori tertentu
    constructor(extraType: String, extra: String) : this(){
        this.extraType = extraType
        this.extra = extra
    }

    // Konstruktor sekunder dipanggil ketika fragment dipanggil melalui Activity Rekomendasi
    constructor(min: Int, max: Int, gameBerat: Boolean, kalkulasiRumit: Boolean, grafis2D: Boolean,
        grafis3D: Boolean, editingVideo: Boolean, pekerjaanRingan: Boolean, isPerforma: Boolean,
        isAcer: Boolean, isAsus: Boolean, isHp: Boolean, isLenovo: Boolean, isMsi: Boolean) : this(){
        this.extraType = "rekomendasi"
        this.min = min
        this.max = max
        this.gameBerat = gameBerat
        this.kalkulasiRumit = kalkulasiRumit
        this.grafis2D = grafis2D
        this.grafis3D = grafis3D
        this.editingVideo = editingVideo
        this.pekerjaanRingan = pekerjaanRingan
        this.isPerforma = isPerforma
        this.isAcer = isAcer
        this.isAsus = isAsus
        this.isHp = isHp
        this.isLenovo = isLenovo
        this.isMsi = isMsi
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_hasil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Menampilkan icon urutkan
        urutkanTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_urutkan,0,0,0)
        urutkanTextView.compoundDrawablePadding = 32

        // Menampilkan icon filter
        filterTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_filter,0,0,0)
        filterTextView.compoundDrawablePadding = 32

        // Menghubungkan variabel RecyclerView dengan RecyclerView sesungguhnya
        hasilRecyclerView = view.findViewById(R.id.hasilRecyclerView)
        hasilRecyclerView.setHasFixedSize(true)

        // Membuat progress bar menjadi nampak
        progressBar = view.findViewById(R.id.hasilProgressBar)
        progressBar.visibility = View.VISIBLE

        // Memanggil data yang diminta dari Firestore sekaligus ditampilkan
        when (extraType) {
            "cari" -> loadLaptopCari()
            "brand" -> loadLaptopBrand()
            "kategori" -> loadLaptopKategori()
            "rekomendasi" -> loadLaptopRekomendasi()
        }

        // Menambahkan event urutkan
        urutkanTextView.setOnClickListener{
            tampilkanOverlayUrutkan()
        }

        // Menambahkan event hitamTransparanLinearLayout
        hitamTransparanLinearLayout.setOnClickListener{
            sembunyikanOverlayUrutkan()
        }
    }

    // Memanggil data semua laptop dari Firestore sekaligus ditampilkan
    private fun loadLaptopCari(){
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
                    listLaptop.filter {l: LaptopTerbaru -> !l.name.contains(extra, true) }
                        .forEach { listLaptop.remove(it) }
                    // Jika tidak ditemukan laptop sesuai masukan pengguna
                    if (listLaptop.isEmpty()){
                        progressBar.visibility = View.GONE
                        val toast = android.widget.Toast.makeText(activity,
                            "Tidak ditemukan laptop sesuai masukan Anda pada basis data kami.",
                            android.widget.Toast.LENGTH_LONG)
                        toast.setGravity(android.view.Gravity.BOTTOM,0,130)
                        toast.show()
                    }
                    else{
                        showRecyclerList()
                        progressBar.visibility = View.GONE
                    }
                }
                else
                    loadLaptopCari()
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
                        document.getString("webcam")!!,
                        document.getLong("performa")!!.toInt(),
                        document.getLong("portabilitas")!!.toInt()))
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
                        document.getString("webcam")!!,
                        document.getLong("performa")!!.toInt(),
                        document.getLong("portabilitas")!!.toInt()))
                }
                if(listLaptop.isNotEmpty()){
                    showRecyclerList()
                    progressBar.visibility = View.GONE
                }
                else
                    loadLaptopKategori()
            }
    }

    // Memanggil data-laptop-sesuai-masukan-pengguna dari Firestore sekaligus ditampilkan
    private fun loadLaptopRekomendasi(){
        val db = FirebaseFirestore.getInstance()
        db.collection("rekomenLaptop")
            .get()
            .addOnSuccessListener {result ->
                for (document in result){
                    rekomenLaptop.add(RekomenLaptop(document.getBoolean("2d")!!,
                        document.getBoolean("3d")!!,
                        document.getString("brand")!!,
                        document.getBoolean("gaming")!!,
                        document.getLong("harga")!!.toInt(),
                        document.getBoolean("kalkulasi")!!,
                        document.getString("nama")!!,
                        document.getBoolean("ringan")!!,
                        document.getBoolean("video")!!
                    ))
                }
                if (rekomenLaptop.isEmpty())
                    loadLaptopRekomendasi()
                else{
                    // Filter berdasar budget
                    rekomenLaptop.filter {r: RekomenLaptop -> r.harga < min || r.harga > max }
                        .forEach { rekomenLaptop.remove(it) }
                    // Filter berdasar keperluan
                    if (gameBerat || kalkulasiRumit || grafis2D || grafis3D || editingVideo){
                        if (gameBerat)
                            rekomenLaptop.filter { r: RekomenLaptop -> !r.gaming }
                                .forEach { rekomenLaptop.remove(it) }
                        if (kalkulasiRumit)
                            rekomenLaptop.filter { r: RekomenLaptop -> !r.kalkulasi }
                                .forEach { rekomenLaptop.remove(it) }
                        if (grafis2D)
                            rekomenLaptop.filter { r: RekomenLaptop -> !r.grafis2D }
                                .forEach { rekomenLaptop.remove(it) }
                        if (grafis3D)
                            rekomenLaptop.filter { r: RekomenLaptop -> !r.grafis3D }
                                .forEach { rekomenLaptop.remove(it) }
                        if (editingVideo)
                            rekomenLaptop.filter { r: RekomenLaptop -> !r.video }
                                .forEach { rekomenLaptop.remove(it) }
                    }
                    else
                        rekomenLaptop.filter { r: RekomenLaptop -> !r.ringan }
                            .forEach { rekomenLaptop.remove(it) }
                    // Filter berdasar brand
                    if (!isAcer)
                        rekomenLaptop.filter { r: RekomenLaptop -> r.brand == "Acer"}
                            .forEach { rekomenLaptop.remove(it) }
                    if (!isAsus)
                        rekomenLaptop.filter { r: RekomenLaptop -> r.brand == "Asus" }
                            .forEach { rekomenLaptop.remove(it) }
                    if (!isHp)
                        rekomenLaptop.filter { r: RekomenLaptop -> r.brand == "HP" }
                            .forEach { rekomenLaptop.remove(it) }
                    if (!isLenovo)
                        rekomenLaptop.filter {r: RekomenLaptop -> r.brand == "Lenovo"}
                            .forEach { rekomenLaptop.remove(it) }
                    if (!isMsi)
                        rekomenLaptop.filter {r: RekomenLaptop -> r.brand == "MSI"}
                            .forEach { rekomenLaptop.remove(it) }
                    rekomenLaptop.trimToSize()
                    // Jika tidak ditemukan laptop sesuai masukan pengguna
                    if (rekomenLaptop.isEmpty()){
                        progressBar.visibility = View.GONE
                        val toast = android.widget.Toast.makeText(activity,
                            "Tidak ditemukan laptop sesuai masukan Anda pada basis data kami.",
                            android.widget.Toast.LENGTH_LONG)
                        toast.setGravity(android.view.Gravity.BOTTOM,0,130)
                        toast.show()
                    }
                    // Jika ditemukan laptop sesuai masukan pengguna
                    else
                        rekomenLaptop.forEach{ getLaptop(it.nama) }
                }
            }
    }

    // Memanggil data satu laptop
    private fun getLaptop(namaLaptop: String){
        var laptop = LaptopTerbaru("","","","","",
            "","","","","","",arrayListOf(),
            arrayListOf(),arrayListOf(),"",arrayListOf(),"","",
            "","","","")
        val db = FirebaseFirestore.getInstance()
        db.collection("spekLaptop")
            .whereEqualTo("namaLaptop", namaLaptop)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    laptop = LaptopTerbaru(namaLaptop,
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
                        document.getLong("portabilitas")!!.toInt())
                }
                if (laptop.name != ""){
                    listLaptop.add(laptop)
                    if (listLaptop.count() == rekomenLaptop.count()){
                        if (isPerforma)
                            listLaptop.sortByDescending{ it.performa }
                        else
                            listLaptop.sortByDescending{ it.portabilitas }
                        progressBar.visibility = View.GONE
                        showRecyclerList()
                    }
                }
                else
                    getLaptop(namaLaptop)
            }
    }
    // Menampilkan laptop-laptop yang diminta pada RecyclerView
    private fun showRecyclerList(){
        hasilRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        val listLaptopTerbaruAdapter = ListLaptopTerbaruAdapter(activity, listLaptop)
        hasilRecyclerView.adapter = listLaptopTerbaruAdapter
    }

    // Menampilkan overlay urutkan
    internal fun tampilkanOverlayUrutkan(){
        urutkanConstraintLayout.visibility = View.VISIBLE
        hitamTransparanLinearLayout.visibility = View.VISIBLE
        isOverlayUrutkan = true
    }

    // Menyembunyikan overlay urutkan
    internal fun sembunyikanOverlayUrutkan(){
        urutkanConstraintLayout.visibility = View.GONE
        hitamTransparanLinearLayout.visibility = View.GONE
        isOverlayUrutkan = false
    }
}