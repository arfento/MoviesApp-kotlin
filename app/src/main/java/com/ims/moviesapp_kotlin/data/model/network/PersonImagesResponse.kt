package com.ims.moviesapp_kotlin.data.model.network

import com.google.gson.annotations.SerializedName
import com.ims.moviesapp_kotlin.data.model.entity.Image
import com.ims.moviesapp_kotlin.data.model.network.BaseListResponse

data class PersonImagesResponse(
    @SerializedName("profiles")
    override var results: List<Image>
) : BaseListResponse<Image>