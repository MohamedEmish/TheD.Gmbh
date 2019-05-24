package com.example.thedgmbh.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductImage {

    @SerializedName("link")
    @Expose
    var link: String = ""
    @SerializedName("height")
    @Expose
    var height: Int = 0
    @SerializedName("width")
    @Expose
    var width: Int = 0
}

