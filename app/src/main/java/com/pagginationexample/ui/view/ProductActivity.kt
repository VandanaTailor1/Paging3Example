package com.pagginationexample.ui.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pagginationexample.databinding.ActivityProductBinding
import com.pagginationexample.ui.adapter.ProductAdapter
import com.pagginationexample.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProductBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapterProduct: ProductAdapter
    val productViewModel : ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productAdapter()
        getProductsApi()
    }



    private fun productAdapter(){
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvProducts.layoutManager = linearLayoutManager
        adapterProduct = ProductAdapter(this@ProductActivity)
        binding.rvProducts.adapter = adapterProduct
    }

    private fun getProductsApi() {
        lifecycleScope.launch {
            productViewModel.pagingDataFlow.collectLatest { pagingData ->
                Log.d("data", "getProductsApi: $pagingData")
                adapterProduct.submitData(pagingData)
            }
        }
    }




}