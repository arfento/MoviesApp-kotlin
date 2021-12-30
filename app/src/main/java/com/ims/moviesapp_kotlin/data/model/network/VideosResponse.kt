package com.ims.moviesapp_kotlin.data.model.network

import com.google.gson.annotations.SerializedName
import com.ims.moviesapp_kotlin.data.model.entity.Video
import com.ims.moviesapp_kotlin.data.model.network.BaseListResponse

data class VideosResponse(
    @SerializedName("results")
    override var results: List<Video>
) : BaseListResponse<Video>
