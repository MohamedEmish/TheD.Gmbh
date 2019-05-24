package com.example.thedgmbh.dataOnline

import com.example.thedgmbh.models.MyData
import retrofit2.Call
import retrofit2.http.GET

interface ProductApi {
    @get:GET("/")
    val data: Call<MyData>
}