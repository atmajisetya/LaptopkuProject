package com.example.laptopku

import android.content.Context
import android.content.Intent
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
import kotlinx.android.synthetic.main.item_kolom_laptop.view.*

class ListLaptopTerbaruAdapter(private val context: Context?, private val listLaptopTerbaru: ArrayList<LaptopTerbaru>) : RecyclerView.Adapter<ListLaptopTerbaruAdapter.ListViewHolder>() {

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
            .apply(RequestOptions().fitCenter().format(DecodeFormat.PREFER_ARGB_8888).override(Target.SIZE_ORIGINAL))
            .into(holder.imgPhoto)

        holder.tvName.text = laptopTerbaru.name
        holder.tvPrice.text = laptopTerbaru.price

        val data = LaptopTerbaru(
            laptopTerbaru.name,
            laptopTerbaru.price,
            laptopTerbaru.photo,
            laptopTerbaru.acadapter,
            laptopTerbaru.audio,
            laptopTerbaru.baterai,
            laptopTerbaru.berat,
            laptopTerbaru.brand,
            laptopTerbaru.chipset,
            laptopTerbaru.cpu,
            laptopTerbaru.dimensi,
            laptopTerbaru.grafis,
            laptopTerbaru.io,
            laptopTerbaru.kategori,
            laptopTerbaru.keyboard,
            laptopTerbaru.komunikasi,
            laptopTerbaru.layar,
            laptopTerbaru.memori,
            laptopTerbaru.os,
            laptopTerbaru.penyimpanan,
            laptopTerbaru.tanggalRilis,
            laptopTerbaru.webcam
        )

        //IKI DINGGO PINDAH NENG DETAIL ACTIVITY BERDASARKAN ITEM YANG DIKLIK
        holder.itemView.img_item_photo.setOnClickListener{
            val intent = Intent(context, DeskripsiLaptopActivity::class.java)
            intent.putExtra("laptopTerbaru", data)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listLaptopTerbaru.size
    }

}