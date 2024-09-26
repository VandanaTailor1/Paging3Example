package com.pagginationexample.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pagginationexample.databinding.ActivityProductsDetailsBinding
import com.pagginationexample.pagination.PaginationScrollListener
import com.pagginationexample.ui.adapter.ProductDetailsAdapter
import com.pagginationexample.ui.model.Product
import com.pagginationexample.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsDetailsBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    val productViewModel: ProductViewModel by viewModels()
    private lateinit var adapterProductDetailsAdapter: ProductDetailsAdapter

    //  private lateinit var
    private val pageStart: Int = 1
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPages: Int = 10
    private var currentPage: Int = pageStart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        observerForAdapter()
        productViewModel.getProductsItem(8,2)
        addScrollListener()
    }

//    private fun productAdapter(listDoc : MutableList<Product>) {
//        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        adapterProductDetailsAdapter= ProductDetailsAdapter(this,listDoc)
//        binding.rvProductDetails.apply {
//            layoutManager = linearLayoutManager
//            adapter = adapterProductDetailsAdapter
//        }
//    }

    private fun setupRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapterProductDetailsAdapter = ProductDetailsAdapter(this, mutableListOf())

        binding.rvProductDetails.apply {
            layoutManager = linearLayoutManager // Set LayoutManager here
            adapter = adapterProductDetailsAdapter
        }
    }


    private fun observerForAdapter(){
        lifecycleScope.launch {
            productViewModel.products.collect {
                Log.d("ProductsDetails", "observerForAdapter: $it")
//               productAdapter(it!!.products.toMutableList())
                it?.products?.let { it1 ->
                    adapterProductDetailsAdapter.updateProducts(it1.toMutableList())
                 }

                // Pagination: check if we are at the last page
                isLoading = false
                if (currentPage == totalPages) {
                    isLastPage = true
                }
            }
        }
    }

    private fun addScrollListener() {
        binding.rvProductDetails.addOnScrollListener(object :
            PaginationScrollListener(binding.rvProductDetails.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                productViewModel.getProductsItem(currentPage, 10) // Fetch next page
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