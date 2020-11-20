package com.example.laptopku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment

class KeperluanFragment : Fragment() {
    // Variabel untuk menyimpan keperluan pengguna
    internal var gameBerat = false
    internal var kalkulasiRumit = false
    internal var grafis2D = false
    internal var grafis3D = false
    internal var editingVideo = false
    internal var pekerjaanRingan = false

    // Variabel untuk merujuk CheckBoxes
    private lateinit var gameBeratCheckBox: CheckBox
    private lateinit var kalkulasiRumitCheckBox: CheckBox
    private lateinit var grafis2DCheckBox: CheckBox
    private lateinit var grafis3DCheckBox: CheckBox
    private lateinit var editingVideoCheckBox: CheckBox
    private lateinit var pekerjaanRinganCheckBox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_keperluan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set event klik CheckBox game berat
        gameBeratCheckBox = view.findViewById(R.id.bermainGameBeratCheckBox)
        gameBeratCheckBox.setOnClickListener {
            gameBerat = gameBeratCheckBox.isChecked
        }

        // Set event klik CheckBox kalkulasi rumit
        kalkulasiRumitCheckBox = view.findViewById(R.id.melakukanKalkulasiRumitCheckBox)
        kalkulasiRumitCheckBox.setOnClickListener{
            kalkulasiRumit = kalkulasiRumitCheckBox.isChecked
        }

        // Set event klik CheckBox membuat grafis 2D
        grafis2DCheckBox = view.findViewById(R.id.membuatGrafis2DCheckBox)
        grafis2DCheckBox.setOnClickListener{
            grafis2D = grafis2DCheckBox.isChecked
        }

        // Set event klik CheckBox membuat grafis 3D
        grafis3DCheckBox = view.findViewById(R.id.membuatGrafis3DCheckBox)
        grafis3DCheckBox.setOnClickListener{
            grafis3D = grafis3DCheckBox.isChecked
        }

        // Set event klik CheckBox editing video
        editingVideoCheckBox = view.findViewById(R.id.editingVideoAnimasiCheckBox)
        editingVideoCheckBox.setOnClickListener{
            editingVideo = editingVideoCheckBox.isChecked
        }

        // Set event klik CheckBox pekerjaan ringan
        pekerjaanRinganCheckBox = view.findViewById(R.id.pekerjaanRinganSajaCheckBox)
        pekerjaanRinganCheckBox.setOnClickListener {
            pekerjaanRingan = pekerjaanRinganCheckBox.isChecked
        }
    }
}