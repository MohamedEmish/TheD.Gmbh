package com.example.thedgmbh.dataOnline

import android.content.Context
import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thedgmbh.models.MyData
import com.example.thedgmbh.models.Product
import com.example.thedgmbh.utils.SaveSharedPreference
import com.google.gson.GsonBuilder
import dagger.Module
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
class RetrofitClass {

    fun getData(context: Context): LiveData<List<Product>> {
        val productList: MutableLiveData<List<Product>> = MutableLiveData()
        val productApi = productApi
        productApi.data.enqueue(object : Callback<MyData> {
            override fun onResponse(call: Call<MyData>, @Nullable response: Response<MyData>) {
                val productsList: List<Product>
                if (response.body() != null) {
                    val data: MyData = response.body()!!
                    productsList = data.myDataList
                    for (product in productsList) {
                        val imageDetail: String =
                            product.image!!.link + "-" + product.image!!.height + "-" + product.image!!.width
                        product.imageDetail = imageDetail
                    }
                    productList.value = productsList
                    SaveSharedPreference.setDb(context, true)
                }
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                Log.d("Pla    :: ", "onCategoryCallFailure: " + t.message)
            }
        })
        return productList
    }

    companion object {

        private const val BASE_URL = "https://limitless-forest-98976.herokuapp.com"

        private val retrofitInstance: Retrofit
            get() {

                val gSon = GsonBuilder()
                    .setLenient()
                    .serializeNulls()
                    .create()

                val client = OkHttpClient.Builder()
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build()

                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gSon))
                    .client(client)
                    .build()
            }

        val productApi: ProductApi
            get() = retrofitInstance.create(ProductApi::class.java)
    }
}