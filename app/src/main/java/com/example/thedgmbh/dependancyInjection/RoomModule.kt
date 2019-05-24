package com.example.thedgmbh.dependancyInjection

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.thedgmbh.repository.ProductRepository
import com.example.thedgmbh.dataLocale.ProductDB
import com.example.thedgmbh.dataLocale.ProductDao
import com.example.thedgmbh.viewModel.CustomViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {

    private val db: ProductDB = Room.databaseBuilder(
        application,
        ProductDB::class.java,
        "Products.db"
    ).build()

    @Provides
    @Singleton
     fun provideProductRepository(dao: ProductDao): ProductRepository {
        return ProductRepository(dao)
    }

    @Provides
    @Singleton
     fun provideProductDao(database: ProductDB): ProductDao {
        return database.dao()
    }

    @Provides
    @Singleton
     fun provideProductDatabase(application: Application): ProductDB {
        return db
    }

    @Provides
    @Singleton
     fun provideViewModelFactory(repository: ProductRepository): ViewModelProvider.Factory {
        return CustomViewModelFactory(repository)
    }
}