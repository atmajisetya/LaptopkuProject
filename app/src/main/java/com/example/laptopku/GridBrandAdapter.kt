package com.example.laptopku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class GridBrandAdapter (private val listBrand: ArrayList<Brand>) : RecyclerView.Adapter<GridBrandAdapter.GridViewHolder>() {
    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_brand)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): GridBrandAdapter.GridViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_grid_brand, viewGroup, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridBrandAdapter.GridViewHolder, position: Int) {
        val brand: Brand = listBrand[position]

        Glide.with(holder.itemView.context)
            .load(brand.photo)
            .apply(RequestOptions().override(Target.SIZE_ORIGINAL))
            .into(holder.imgPhoto)
    }

    override fun getItemCount(): Int {
        return listBrand.size
    }

}