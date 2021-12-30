package com.ims.moviesapp_kotlin.data.model.network

interface BaseListResponse<T> {
    var results: List<T>
}
