package com.pagginationexample.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pagginationexample.databinding.ActivityProductsDetailsBinding
import com.pagginationexample.pagination.PaginationScrollListener
import com.pagginationexample.viewmodel.ProductViewModel

class ProductsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsDetailsBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    val productViewModel: ProductViewModel by viewModels()

    //  private lateinit var
    private val pageStart: Int = 1
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPages: Int = 1
    private var currentPage: Int = pageStart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun productAdapter() {
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvProductDetails.layoutManager = linearLayoutManager
    }

    private fun addScrollListener() {
        binding.rvProductDetails.addOnScrollListener(object :
            PaginationScrollListener(binding.rvProductDetails.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                productViewModel.getProductsItem(3, 200)
            }

            override fun getTotalPageCount(): Int {
                return totalPages
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }

}