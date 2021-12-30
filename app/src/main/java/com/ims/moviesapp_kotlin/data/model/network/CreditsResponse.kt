package com.ims.moviesapp_kotlin.data.model.network

import com.google.gson.annotations.SerializedName
import com.ims.moviesapp_kotlin.data.model.entity.Cast
import com.ims.moviesapp_kotlin.data.model.network.BaseListResponse

data class CreditsResponse(
    @SerializedName("cast")
    override var results: List<Cast>
) : BaseListResponse<Cast>


