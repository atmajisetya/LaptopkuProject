package com.example.laptopku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListSpekLaptopAdapter(private val listSpekLaptop: ArrayList<SpekLaptop>): RecyclerView.Adapter<ListSpekLaptopAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //inisilasisasi view yang ada di dalam layout item_spek_laptop
        var tvNamaSpek: TextView = itemView.findViewById(R.id.tv_namaSpek_laptop)
        var tvIsiSpek: TextView = itemView.findViewById(R.id.tv_isiSpek_laptop)
    }

    //dinggo menampilkan layout item_spek_laptop
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_spek_laptop, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val spekLaptop = listSpekLaptop[position]

        //holder.tvNamaSpek.text = spekLaptop.namaSpek
        //holder.tvIsiSpek.text = spekLaptop.isiSpek
    }

    override fun getItemCount(): Int {
        return listSpekLaptop.size
    }
}