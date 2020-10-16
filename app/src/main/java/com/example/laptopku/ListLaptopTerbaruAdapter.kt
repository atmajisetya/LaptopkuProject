package com.example.laptopku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListLaptopTerbaruAdapter(private val listLaptopTerbaru: ArrayList<LaptopTerbaru>) : RecyclerView.Adapter<ListLaptopTerbaruAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //inisilasisasi view yang ada di dalam layout item_kolom_laptop
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvPrice : TextView = itemView.findViewById(R.id.tv_item_price)
        var imgPhoto : ImageView = itemView.findViewById(R.id.img_item_photo)

    }

    //dinggo menampilkan layout item_kolom_laptop
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_kolom_laptop, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val laptopTerbaru = listLaptopTerbaru[position]

        //Glide digunakan untuk menampilkan gambar
        Glide.with(holder.itemView.context)
            .load(laptopTerbaru.photo)
            .apply(RequestOptions().override(55,55))
            .into(holder.imgPhoto)

        holder.tvName.text = laptopTerbaru.name
        holder.tvPrice.text = laptopTerbaru.price
    }

    override fun getItemCount(): Int {
        return listLaptopTerbaru.size
    }

}