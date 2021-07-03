package com.example.flicks.model

import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("youtube")
    val listYoutube: ArrayList<Youtube>
    )
data class Youtube(val name: String, val source: String)
