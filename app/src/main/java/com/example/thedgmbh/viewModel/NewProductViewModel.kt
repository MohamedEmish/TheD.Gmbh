package com.example.thedgmbh.viewModel

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import com.example.thedgmbh.repository.ProductRepository
import com.example.thedgmbh.models.Product

class NewProductViewModel constructor(private val repository: ProductRepository) : ViewModel() {

    fun addNewItemToDatabase(product: Product) {
        AddItemTask().execute(product)
    }
    private inner class AddItemTask : AsyncTask<Product, Void, Void>() {

        override fun doInBackground(vararg item: Product): Void? {
            repository.insertProduct(item[0])
            return null
        }
    }
}