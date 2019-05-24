package com.example.thedgmbh.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MyData {
    @SerializedName("data")
    @Expose
    var myDataList: List<Product> = ArrayList()

    @SerializedName("count")
    @Expose
    var count : Int = 0
}
