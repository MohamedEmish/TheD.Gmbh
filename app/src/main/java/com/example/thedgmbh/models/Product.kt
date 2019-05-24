package com.example.thedgmbh.models

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class Product {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id : Int = 0

    @SerializedName("name")
    @Expose
    var name : String = ""

    @SerializedName("productDescription")
    @Expose
    var description : String = ""

    @SerializedName("price")
    @Expose
    var price: Int = 0

    @SerializedName("image")
    @Expose
    @Ignore
    var image : ProductImage? = null

    var imageDetail : String = ""
}