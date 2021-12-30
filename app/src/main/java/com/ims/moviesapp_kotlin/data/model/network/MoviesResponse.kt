package com.ims.moviesapp_kotlin.data.model.network

import com.google.gson.annotations.SerializedName
import com.ims.moviesapp_kotlin.data.model.entity.Movie
import com.ims.moviesapp_kotlin.data.model.network.BasePageListResponse


data class MoviesResponse(
    @SerializedName("page")
    override var page: Int,

    @SerializedName("results")
    override var results: List<Movie>
) : BasePageListResponse<Movie>
