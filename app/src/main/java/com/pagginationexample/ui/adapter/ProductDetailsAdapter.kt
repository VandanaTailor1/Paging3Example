package com.pagginationexample.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pagginationexample.databinding.ProductListBinding
import com.pagginationexample.ui.model.Product

class ProductDetailsAdapter(
   val context: Context,
    val listProducts : MutableList<Product>
) : RecyclerView.Adapter<ProductDetailsAdapter.ViewHolder>() {

    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false
    private var errorMsg: String? = ""

    fun addProducts(newProducts: List<Product>) {
        val startPosition = listProducts.size
        listProducts.addAll(newProducts)
        notifyItemRangeInserted(startPosition, newProducts.size)
    }

    inner class ViewHolder( val binding : ProductListBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =ProductListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvTitle.text= listProducts[position].title
        holder.binding.tvDescription.text=listProducts[position].description
        Glide.with(context)
            .load(listProducts[position].thumbnail)
            .into(holder.binding.ivThumbnail)
    }

}