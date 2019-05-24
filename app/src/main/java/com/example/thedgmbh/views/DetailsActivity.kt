package com.example.thedgmbh.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.thedgmbh.R
import com.example.thedgmbh.models.Product
import com.example.thedgmbh.utils.RoomApplication
import com.example.thedgmbh.viewModel.ProductViewModel
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    private var productId: Int = 0

    @set:Inject
    var viewModelFactory: ViewModelProvider.Factory? = null

    private var productViewModel: ProductViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        (this.application as RoomApplication)
            .getApplicationComponent()?.inject(this)

        val extras = intent.extras
        if (extras != null) {
            productId = extras.getInt("productId")
            val categoryTitle = extras.getString("productName")
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)
            supportActionBar!!.title = categoryTitle
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        productViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProductViewModel::class.java)

        setupData()
    }

    private fun setupData() {
        productViewModel!!.getListItemById(productId).observe(this, Observer<Product> { product ->
            if (product != null) {
                tv_product_description.text = product.description
                tv_product_price.text = product.price.toString()

                val data: String = product.imageDetail

                val phase1 = data.split("-")
                val link = phase1[0]

                Glide.with(iv_product)
                    .load(link)
                    .apply(RequestOptions().override(1000, 1000))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(iv_product)
            }
        })
    }
}
