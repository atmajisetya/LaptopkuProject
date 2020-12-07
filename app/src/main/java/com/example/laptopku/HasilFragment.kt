package com.example.laptopku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_hasil.*
import kotlin.math.absoluteValue

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
    private var pekerjaanRingan = false
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
    private val listLaptopFilter: ArrayList<LaptopTerbaru> = arrayListOf()
    private val rekomenLaptop: ArrayList<RekomenLaptop> = arrayListOf()

    // Boolean untuk mengetahui apakah overlay sedang tampil
    internal var isOverlayUrutkan = false
    internal var isOverlayFilter = false

    // String untuk mengetahui urutkan atau filter saat ini
    private var urutkanBerdasar = "Tidak"
    private var filterBerdasar = "Semua"

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
            "brand" -> {
                loadLaptopBrand()
                palingSesuaiButton.visibility = View.INVISIBLE
            }
            "kategori" -> {
                loadLaptopKategori()
                palingSesuaiButton.visibility = View.INVISIBLE
            }
            "rekomendasi" -> {
                loadLaptopRekomendasi()
                palingSesuaiButton.visibility = View.INVISIBLE
            }
        }

        // Menambahkan event urutkan
        urutkanTextView.setOnClickListener{
            if (progressBar.visibility == View.GONE){
                if (isOverlayFilter)
                    sembunyikanOverlayFilter()
                tampilkanOverlayUrutkan()
            }
        }
        palingSesuaiButton.setOnClickListener{
            if (urutkanBerdasar != "Paling Sesuai" && palingSesuaiButton.visibility == View.VISIBLE){
                pilihButtonUrutkan("Paling Sesuai")
                progressBar.visibility = View.VISIBLE
                listLaptop.sortBy{ it.name.compareTo(extra, true).absoluteValue }
                showRecyclerList()
                progressBar.visibility = View.GONE
            }
        }
        terbaruButton.setOnClickListener{
            if (urutkanBerdasar != "Terbaru"){
                pilihButtonUrutkan("Terbaru")
                progressBar.visibility = View.VISIBLE
                listLaptop.sortByDescending{ it.tanggalRilis }
                showRecyclerList()
                progressBar.visibility = View.GONE
            }
        }
        hargaTertinggiButton.setOnClickListener{
            if (urutkanBerdasar != "Harga Tertinggi"){
                pilihButtonUrutkan("Harga Tertinggi")
                progressBar.visibility = View.VISIBLE
                listLaptop.sortByDescending{ l -> l.price.filter { it.isDigit() }.toInt() }
                showRecyclerList()
                progressBar.visibility = View.GONE
            }
        }
        hargaTerendahButton.setOnClickListener{
            if (urutkanBerdasar != "Harga Terendah"){
                pilihButtonUrutkan("Harga Terendah")
                progressBar.visibility = View.VISIBLE
                listLaptop.sortBy{ l -> l.price.filter { it.isDigit() }.toInt() }
                showRecyclerList()
                progressBar.visibility = View.GONE
            }
        }
        performaButton.setOnClickListener{
            if (urutkanBerdasar != "Performa"){
                pilihButtonUrutkan("Performa")
                progressBar.visibility = View.VISIBLE
                listLaptop.sortByDescending{ it.performa }
                showRecyclerList()
                progressBar.visibility = View.GONE
            }
        }
        portabilitasButton.setOnClickListener{
            if (urutkanBerdasar != "Portabilitas"){
                pilihButtonUrutkan("Portabilitas")
                progressBar.visibility = View.VISIBLE
                listLaptop.sortByDescending{ it.portabilitas }
                showRecyclerList()
                progressBar.visibility = View.GONE
            }
        }

        // Menambahkan event filter
        filterTextView.setOnClickListener{
            if (progressBar.visibility == View.GONE){
                if (isOverlayUrutkan)
                    sembunyikanOverlayUrutkan()
                tampilkanOverlayFilter()
            }
        }
        gamingButton.setOnClickListener{
            if (filterBerdasar != "Gaming")
                filter("Gaming")
            else{
                gamingButton.setTextColor(-6908266) //abu
                gamingButton.setBackgroundResource(R.drawable.bg_button_putih)
                filter("Semua")
            }
        }
        profesionalButton.setOnClickListener{
            if (filterBerdasar != "Profesional")
                filter("Profesional")
            else{
                profesionalButton.setTextColor(-6908266) //abu
                profesionalButton.setBackgroundResource(R.drawable.bg_button_putih)
                filter("Semua")
            }
        }
        pelajarButton.setOnClickListener{
            if (filterBerdasar != "Pelajar")
                filter("Pelajar")
            else{
                pelajarButton.setTextColor(-6908266) //abu
                pelajarButton.setBackgroundResource(R.drawable.bg_button_putih)
                filter("Semua")
            }
        }
        workstationButton.setOnClickListener{
            if (filterBerdasar != "Workstation")
                filter("Workstation")
            else{
                workstationButton.setTextColor(-6908266) //abu
                workstationButton.setBackgroundResource(R.drawable.bg_button_putih)
                filter("Semua")
            }
        }

        // Menambahkan event hitamTransparanLinearLayout
        hitamTransparanLinearLayout.setOnClickListener{
            if (isOverlayUrutkan)
                sembunyikanOverlayUrutkan()
            else
                sembunyikanOverlayFilter()
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
                        document.getString("webcam")!!,
                        document.getLong("performa")!!.toInt(),
                        document.getLong("portabilitas")!!.toInt()))
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
                        listLaptop.sortBy{ it.name.compareTo(extra, true).absoluteValue }
                        showRecyclerList()
                        progressBar.visibility = View.GONE
                        urutkanBerdasar = "Paling Sesuai"
                        palingSesuaiButton.setBackgroundResource(R.drawable.bg_button_ungu)
                        palingSesuaiButton.setTextColor(-13434727) //biru
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
                    showRecyclerList(extra)
                    progressBar.visibility = View.GONE
                    when (extra) {
                        "Gaming" -> {
                            filterBerdasar = "Gaming"
                            gamingButton.setBackgroundResource(R.drawable.bg_button_ungu)
                            gamingButton.setTextColor(-13434727) //biru
                        }
                        "Pelajar" -> {
                            filterBerdasar = "Pelajar"
                            pelajarButton.setBackgroundResource(R.drawable.bg_button_ungu)
                            pelajarButton.setTextColor(-13434727) //biru
                        }
                        "Profesional" -> {
                            filterBerdasar = "Profesional"
                            profesionalButton.setBackgroundResource(R.drawable.bg_button_ungu)
                            profesionalButton.setTextColor(-13434727) //biru
                        }
                        else -> {
                            filterBerdasar = "Workstation"
                            workstationButton.setBackgroundResource(R.drawable.bg_button_ungu)
                            workstationButton.setTextColor(-13434727) //biru
                        }
                    }
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
                        if (isPerforma){
                            listLaptop.sortByDescending{ it.performa }
                            urutkanBerdasar = "Performa"
                            performaButton.setBackgroundResource(R.drawable.bg_button_ungu)
                            performaButton.setTextColor(-13434727) //biru
                        }
                        else{
                            listLaptop.sortByDescending{ it.portabilitas }
                            urutkanBerdasar = "Portabilitas"
                            portabilitasButton.setBackgroundResource(R.drawable.bg_button_ungu)
                            portabilitasButton.setTextColor(-13434727) //biru
                        }
                        progressBar.visibility = View.GONE
                        showRecyclerList()
                    }
                }
                else
                    getLaptop(namaLaptop)
            }
    }

    // Melakukan filter laptop
    private fun filter(filterBerdasar: String){
        if (filterBerdasar != "Semua"){
            pilihButtonFilter(filterBerdasar)
            progressBar.visibility = View.VISIBLE
            showRecyclerList(filterBerdasar)
            progressBar.visibility = View.GONE
        }
        else{
            this.filterBerdasar = "Semua"
            progressBar.visibility = View.VISIBLE
            showRecyclerList()
            progressBar.visibility = View.GONE
        }
    }

    // Menampilkan laptop-laptop yang diminta pada RecyclerView
    private fun showRecyclerList(){
        hasilRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        val listLaptopTerbaruAdapter = ListLaptopTerbaruAdapter(activity, listLaptop)
        hasilRecyclerView.adapter = listLaptopTerbaruAdapter
    }

    // Menampilkan laptop-laptop sesuai filter pada RecyclerView
    private fun showRecyclerList(filter: String){
        hasilRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        val listLaptopTerbaruAdapter = ListLaptopTerbaruAdapter(activity, ArrayList(listLaptop.filter{ it.kategori.contains(filter) }))
        hasilRecyclerView.adapter = listLaptopTerbaruAdapter
    }

    private fun tampilkanOverlayUrutkan(){
        urutkanTextView.setBackgroundColor(-6908266) //abu
        urutkanConstraintLayout.visibility = View.VISIBLE
        hitamTransparanLinearLayout.visibility = View.VISIBLE
        isOverlayUrutkan = true
    }

    private fun tampilkanOverlayFilter(){
        filterTextView.setBackgroundColor(-6908266) //abu
        filterConstraintLayout.visibility = View.VISIBLE
        hitamTransparanLinearLayout.visibility = View.VISIBLE
        isOverlayFilter = true
    }

    internal fun sembunyikanOverlayUrutkan(){
        urutkanTextView.setBackgroundColor(-1) //putih
        urutkanConstraintLayout.visibility = View.GONE
        hitamTransparanLinearLayout.visibility = View.GONE
        isOverlayUrutkan = false
    }

    internal fun sembunyikanOverlayFilter(){
        filterTextView.setBackgroundColor(-1) //putih
        filterConstraintLayout.visibility = View.GONE
        hitamTransparanLinearLayout.visibility = View.GONE
        isOverlayFilter = false
    }

    private fun pilihButtonUrutkan(button: String){
        urutkanBerdasar = button
        if (button == "Paling Sesuai"){
            palingSesuaiButton.setTextColor(-13434727) //biru
            palingSesuaiButton.setBackgroundResource(R.drawable.bg_button_ungu)
        }
        else if (palingSesuaiButton.visibility == View.VISIBLE){
            palingSesuaiButton.setTextColor(-6908266) //abu
            palingSesuaiButton.setBackgroundResource(R.drawable.bg_button_putih)
        }
        if (button == "Terbaru"){
            terbaruButton.setTextColor(-13434727) //biru
            terbaruButton.setBackgroundResource(R.drawable.bg_button_ungu)
        }
        else{
            terbaruButton.setTextColor(-6908266) //abu
            terbaruButton.setBackgroundResource(R.drawable.bg_button_putih)
        }
        if (button == "Harga Tertinggi"){
            hargaTertinggiButton.setTextColor(-13434727) //biru
            hargaTertinggiButton.setBackgroundResource(R.drawable.bg_button_ungu)
        }
        else{
            hargaTertinggiButton.setTextColor(-6908266) //abu
            hargaTertinggiButton.setBackgroundResource(R.drawable.bg_button_putih)
        }
        if (button == "Harga Terendah"){
            hargaTerendahButton.setTextColor(-13434727) //biru
            hargaTerendahButton.setBackgroundResource(R.drawable.bg_button_ungu)
        }
        else{
            hargaTerendahButton.setTextColor(-6908266) //abu
            hargaTerendahButton.setBackgroundResource(R.drawable.bg_button_putih)
        }
        if (button == "Performa"){
            performaButton.setTextColor(-13434727) //biru
            performaButton.setBackgroundResource(R.drawable.bg_button_ungu)
        }
        else{
            performaButton.setTextColor(-6908266) //abu
            performaButton.setBackgroundResource(R.drawable.bg_button_putih)
        }
        if (button == "Portabilitas"){
            portabilitasButton.setTextColor(-13434727)
            portabilitasButton.setBackgroundResource(R.drawable.bg_button_ungu)
        }
        else{
            portabilitasButton.setTextColor(-6908266) //abu
            portabilitasButton.setBackgroundResource(R.drawable.bg_button_putih)
        }
    }

    private fun pilihButtonFilter(button: String){
        filterBerdasar = button
        if (button == "Gaming"){
            gamingButton.setTextColor(-13434727) //biru
            gamingButton.setBackgroundResource(R.drawable.bg_button_ungu)
        }
        else{
            gamingButton.setTextColor(-6908266) //abu
            gamingButton.setBackgroundResource(R.drawable.bg_button_putih)
        }
        if (button == "Profesional"){
            profesionalButton.setTextColor(-13434727) //biru
            profesionalButton.setBackgroundResource(R.drawable.bg_button_ungu)
        }
        else{
            profesionalButton.setTextColor(-6908266) //abu
            profesionalButton.setBackgroundResource(R.drawable.bg_button_putih)
        }
        if (button == "Pelajar"){
            pelajarButton.setTextColor(-13434727) //biru
            pelajarButton.setBackgroundResource(R.drawable.bg_button_ungu)
        }
        else{
            pelajarButton.setTextColor(-6908266) //abu
            pelajarButton.setBackgroundResource(R.drawable.bg_button_putih)
        }
        if (button == "Workstation"){
            workstationButton.setTextColor(-13434727) //biru
            workstationButton.setBackgroundResource(R.drawable.bg_button_ungu)
        }
        else{
            workstationButton.setTextColor(-6908266) //abu
            workstationButton.setBackgroundResource(R.drawable.bg_button_putih)
        }
    }
}