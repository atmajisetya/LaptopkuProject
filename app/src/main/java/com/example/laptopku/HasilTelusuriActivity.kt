package com.example.laptopku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HasilTelusuriActivity : AppCompatActivity() {
    //inisiasi TextView
    private lateinit var textView: android.widget.TextView

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
    }
}