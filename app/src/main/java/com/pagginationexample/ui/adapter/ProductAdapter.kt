package com.pagginationexample.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pagginationexample.databinding.ProductListBinding
import com.pagginationexample.ui.model.Product

class ProductAdapter(
    var context: Context
) : PagingDataAdapter<Product, ProductAdapter.ViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = getItem(position)
        listItem?.let {
            holder.binding.tvTitle.text = it.title
            holder.binding.tvDescription.text = it.description
            Glide.with(context)
                .load(it.thumbnail)
                .into(holder.binding.ivThumbnail)
        }
    }

   inner class ViewHolder(val binding: ProductListBinding) :
        RecyclerView.ViewHolder(binding.root)



}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        // Compare the unique identifier (like ID)
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        // Compare all fields to determine content equality
        return oldItem == newItem
    }

}


