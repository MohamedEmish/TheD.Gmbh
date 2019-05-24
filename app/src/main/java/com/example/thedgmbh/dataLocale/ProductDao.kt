package com.example.thedgmbh.dataLocale

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.thedgmbh.models.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM Product WHERE id = :productId")
    fun getProductDetail(productId:Int): LiveData<Product>

    @Insert(onConflict = REPLACE)
    fun insertProduct(product: Product): Long

    @Delete
    fun deleteListItem(product: Product)

}