package com.example.laptopku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment

class BrandFragment : Fragment() {
    // Variabel untuk menyimpan preferensi brand pengguna
    internal var isAcer = false
    internal var isAsus = false
    internal var isHp = false
    internal var isLenovo = false
    internal var isMsi = false

    // Variabel untuk merujuk ke CheckBoxes
    private lateinit var acerCheckBox: CheckBox
    private lateinit var asusCheckBox: CheckBox
    private lateinit var hpCheckBox: CheckBox
    private lateinit var lenovoCheckBox: CheckBox
    private lateinit var msiCheckBox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_brand, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set event klik CheckBox Acer
        acerCheckBox = view.findViewById(R.id.acerCheckBox)
        acerCheckBox.setOnClickListener{
            isAcer = acerCheckBox.isChecked
        }

        // Set event klik CheckBox ASUS
        asusCheckBox = view.findViewById(R.id.asusCheckBox)
        asusCheckBox.setOnClickListener{
            isAsus = asusCheckBox.isChecked
        }

        // Set event klik CheckBox HP
        hpCheckBox = view.findViewById(R.id.hpCheckBox)
        hpCheckBox.setOnClickListener{
            isHp = hpCheckBox.isChecked
        }

        // Set event klik CheckBox Lenovo
        lenovoCheckBox = view.findViewById(R.id.lenovoCheckBox)
        lenovoCheckBox.setOnClickListener{
            isLenovo = lenovoCheckBox.isChecked
        }

        // Set event klik CheckBox Msi
        msiCheckBox = view.findViewById(R.id.msiCheckBox)
        msiCheckBox.setOnClickListener{
            isMsi = msiCheckBox.isChecked
        }
    }
}