package com.ims.moviesapp_kotlin.data.model.network

import com.google.gson.annotations.SerializedName
import com.ims.moviesapp_kotlin.data.model.entity.Credit
import com.ims.moviesapp_kotlin.data.model.network.BaseListResponse

data class PersonCreditsResponse(
    @SerializedName("cast")
    override var results: List<Credit>
) : BaseListResponse<Credit>