package com.warnet.laptopku

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
        // Inisiasi view yang ada di layout item_kolom_laptop
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvPrice : TextView = itemView.findViewById(R.id.tv_item_price)
        var imgPhoto : ImageView = itemView.findViewById(R.id.img_item_photo)

    }

    // Untuk menampilkan layout item_kolom_laptop
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_kolom_laptop, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val laptopTerbaru = listLaptopTerbaru[position]

        // Glide digunakan untuk menampilkan gambar
        Glide.with(holder.itemView.context)
            .load(laptopTerbaru.photo)
            .apply(RequestOptions().fitCenter().format(DecodeFormat.PREFER_ARGB_8888).override(Target.SIZE_ORIGINAL))
            .into(holder.imgPhoto)

        holder.tvName.text = laptopTerbaru.name
        holder.tvPrice.text = laptopTerbaru.price

        // Mendaftarkan event klik pada masing-masing laptop untuk pindah ke Activity DeskripsiLaptop
        holder.itemView.itemKolomLaptop.setOnClickListener{
            val intent = Intent(context, DeskripsiLaptopActivity::class.java)
            intent.putExtra("laptopTerbaru", laptopTerbaru)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listLaptopTerbaru.size
    }

}