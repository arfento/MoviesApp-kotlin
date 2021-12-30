package com.ims.moviesapp_kotlin.data.model.network

import com.google.gson.annotations.SerializedName
import com.ims.moviesapp_kotlin.data.model.entity.Genre
import com.ims.moviesapp_kotlin.data.model.network.BaseListResponse

data class GenresResponse(
    @SerializedName("genres")
    override var results: List<Genre>
) : BaseListResponse<Genre>
