package com.example.laptopku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class ListLaptopFavoriteAdapter(private val listLaptopFavorite: ArrayList<LaptopFavorite>) : RecyclerView.Adapter<ListLaptopFavoriteAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //inisilasisasi view yang ada di dalam layout item_laptop_favorite
        var laptopFavoriteImageView : ImageView = itemView.findViewById(R.id.laptopFavoriteImageView)
        var laptopFavoriteTextView: TextView = itemView.findViewById(R.id.laptopFavoriteTextView)
        var bandingkanImageView : ImageView = itemView.findViewById(R.id.itemLaptopFavoriteBandingkanImageView)
    }

    //untuk menampilkan layout item_kolom_favorite
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListLaptopFavoriteAdapter.ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_laptop_favorite, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListLaptopFavoriteAdapter.ListViewHolder, position: Int) {
        val laptopFavorite = listLaptopFavorite[position]

        //Glide digunakan untuk menampilkan gambar
        Glide.with(holder.itemView.context)
            .load(laptopFavorite.photo)
            .apply(RequestOptions().fitCenter().format(DecodeFormat.PREFER_ARGB_8888).override(Target.SIZE_ORIGINAL))
            .into(holder.laptopFavoriteImageView)

        holder.laptopFavoriteTextView.text = laptopFavorite.name
    }

    override fun getItemCount(): Int {
        return listLaptopFavorite.size
    }
}