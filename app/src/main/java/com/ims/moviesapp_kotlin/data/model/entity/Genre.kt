package com.ims.moviesapp_kotlin.data.model.entity

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,
)