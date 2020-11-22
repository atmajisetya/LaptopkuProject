package com.example.laptopku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment

class PrioritasFragment : Fragment() {
    // Variabel untuk menyimpan preferensi prioritas pengguna
    internal var isPerforma = false
    internal var isPortabilitas = false

    // Variabel untuk merujuk ke CheckBoxes
    private lateinit var performaRadioButton: RadioButton
    private lateinit var portabilitasRadioButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_prioritas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set event klik RadioButton performa
        performaRadioButton = view.findViewById(R.id.performaRadioButton)
        performaRadioButton.setOnClickListener{
            isPerforma = true
            isPortabilitas = false
        }

        // Set event klik RadioButton portabilitas
        portabilitasRadioButton = view.findViewById(R.id.portabilitasRadioButton)
        portabilitasRadioButton.setOnClickListener{
            isPerforma = false
            isPortabilitas = true
        }
    }
}