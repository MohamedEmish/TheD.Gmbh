package com.example.thedgmbh.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thedgmbh.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomViewModelFactory @Inject
constructor(private val repository: ProductRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> ProductViewModel(repository) as T
            modelClass.isAssignableFrom(NewProductViewModel::class.java) -> NewProductViewModel(repository) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
