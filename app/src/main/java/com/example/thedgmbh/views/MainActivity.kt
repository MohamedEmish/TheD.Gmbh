package com.example.thedgmbh.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thedgmbh.R
import com.example.thedgmbh.adapters.ProductAdapter
import com.example.thedgmbh.models.Product
import com.example.thedgmbh.utils.RoomApplication
import com.example.thedgmbh.utils.SaveSharedPreference
import com.example.thedgmbh.viewModel.NewProductViewModel
import com.example.thedgmbh.viewModel.ProductViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    private var productRecycleView: RecyclerView? = null
    private var productAdapter: ProductAdapter? = null
    private var progressBar: ProgressBar? = null

    @set:Inject
    var viewModelFactory: ViewModelProvider.Factory? = null
    private var productViewModel: ProductViewModel? = null
    private var newProductViewModel: NewProductViewModel? = null
    private var listOfData: MutableList<Product>? = null
    private var parent: ConstraintLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        (this.application as RoomApplication)
            .getApplicationComponent()?.inject(this)

        progressBar = findViewById(R.id.pb_main)
        parent = findViewById(R.id.parent)


        productRecycleView = findViewById(R.id.rv_products_list)
        productRecycleView!!.layoutManager = GridLayoutManager(this, 2)
        productRecycleView!!.setHasFixedSize(true)
        productAdapter = ProductAdapter(this)
        productRecycleView!!.adapter = productAdapter
        productAdapter!!.setOnItemClickListener(object : ProductAdapter.OnItemClickListener {
            override fun onItemClick(product: Product) {
                val productsIntent = Intent(this@MainActivity, DetailsActivity::class.java)
                productsIntent.putExtra("productId", product.id)
                productsIntent.putExtra("productName", product.name)
                startActivity(productsIntent)
            }
        })

    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onResume(){
        super.onResume()

        productViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ProductViewModel::class.java)

        newProductViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewProductViewModel::class.java)

        if (isNetworkAvailable()) {
            if (SaveSharedPreference.getUDb(this)) {
                oldData()
            }

        } else {
            noInternet()
            if (SaveSharedPreference.getUDb(this)) {
                oldData()
            }else{
                Toast.makeText(this,"No Data stored .. failed to load \nPlease turn on internet and try again",Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private fun noInternet() {
        progressBar!!.visibility = View.GONE
        Snackbar.make(
            parent!!,
            "No Internet Connection",
            Snackbar.LENGTH_LONG)
            .setDuration(3000)
            .show()

        val fAb: FloatingActionButton = findViewById(R.id.fab)
        if (!SaveSharedPreference.getUDb(this)) {
            fAb.visibility = View.VISIBLE
        }
        fAb.setOnClickListener {
            progressBar!!.visibility = View.VISIBLE
            fAb.visibility = View.GONE
            if (isNetworkAvailable()) {
                newData()
            } else {
                noInternet()
            }
        }
    }

    private fun oldData() {
        productViewModel!!.listProducts.observe(this,
            Observer<List<Product>> { products ->
                if (listOfData == null) {
                    setListData(products)
                }
            })
    }

    private fun setListData(products: List<Product>?) {
        productAdapter!!.submitList(products)
        progressBar!!.visibility = View.GONE
        productAdapter!!.notifyDataSetChanged()
    }

    private fun newData() {
        productViewModel!!.getOnLineData(this).observe(this,
            Observer<List<Product>> { products ->
                if (listOfData == null) {
                    setListData(products)
                    newData()
                    Snackbar.make(
                        parent!!,
                        "Data is up to date",
                        Snackbar.LENGTH_LONG)
                        .setDuration(3000)
                        .show()
                    for (product in products) {
                        newProductViewModel!!.addNewItemToDatabase(product)
                    }
                    SaveSharedPreference.setDb(this@MainActivity, true)
                }
            })
    }
}
