package com.example.thedgmbh.dataLocale

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thedgmbh.models.Product

@Database(entities = [Product::class], version = 1,exportSchema = false)
abstract class ProductDB : RoomDatabase() {
    abstract fun dao(): ProductDao

}