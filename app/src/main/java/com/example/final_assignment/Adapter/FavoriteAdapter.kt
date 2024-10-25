package com.example.final_assignment.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.final_assignment.Model.ItemsModel
import com.example.final_assignment.R
import com.example.final_assignment.databinding.ViewholderFavoriteBinding

class FavoriteAdapter(
    private var favoriteList: List<ItemsModel>,
    private val onFavoriteClick: (ItemsModel) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: ViewholderFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ViewholderFavoriteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = favoriteList[position]

        // Set the item title and price
        holder.binding.titleTxt.text = item.title
        holder.binding.feeEachItem.text = "RM ${item.price}"

        // Load the image using Glide
        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])  // Assuming the first URL in the list is the image you want to load
            .apply(RequestOptions().transform(CenterCrop()))
            .into(holder.binding.pic)

        // Set the favorite button image resource
        holder.binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_red)

        // Set the onClickListener for the favorite button
        holder.binding.favoriteBtn.setOnClickListener {
            onFavoriteClick(item)
        }
    }

    override fun getItemCount(): Int = favoriteList.size

    // Method to update the favorite list
    fun updateFavorites(newFavorites: List<ItemsModel>) {
        favoriteList = newFavorites
        notifyDataSetChanged()
    }
}
