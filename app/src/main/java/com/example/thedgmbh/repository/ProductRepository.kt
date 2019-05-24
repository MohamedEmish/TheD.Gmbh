package com.example.thedgmbh.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.thedgmbh.dataLocale.ProductDao
import com.example.thedgmbh.dataOnline.RetrofitClass
import com.example.thedgmbh.models.Product
import javax.inject.Inject

class ProductRepository(dao: ProductDao) {

    @set:Inject
    private var productDao: ProductDao = dao

    fun getAllProducts(): LiveData<List<Product>> {
        return productDao.getAllProducts()
    }

    fun getProductDetail(productId: Int): LiveData<Product> {
        return productDao.getProductDetail(productId)
    }

    fun insertProduct(product: Product): Long? {
        return productDao.insertProduct(product)
    }

    fun getOnlineData(context: Context):LiveData<List<Product>>{
        val retrofitClass = RetrofitClass()
        return retrofitClass.getData(context)
    }
}