package com.example.thedgmbh.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.thedgmbh.repository.ProductRepository
import com.example.thedgmbh.models.Product

class ProductViewModel constructor(private val repository: ProductRepository) : ViewModel() {

    fun getListItemById(itemId: Int): LiveData<Product> {
        return repository.getProductDetail(itemId)
    }

    fun getOnLineData(context: Context): LiveData<List<Product>>{
        return repository.getOnlineData(context)
    }

    val listProducts: LiveData<List<Product>>
        get() = repository.getAllProducts()

}