package com.example.laptopku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class KeperluanFragment(min: Int, max: Int) : Fragment() {
    // Variabel untuk menyimpan range budget pengguna
    internal var min = 0
    internal var max = 99999999

    // Blok inisiasi fragment
    init{
        this.min = min
        this.max = max
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_keperluan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // START DEBUGGING
        val toast = android.widget.Toast.makeText(activity,
            "min: $min, max: $max",
            android.widget.Toast.LENGTH_LONG)
        toast.setGravity(android.view.Gravity.BOTTOM,0,130)
        toast.show()
        // END DEBUGGING
    }
}