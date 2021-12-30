package com.ims.moviesapp_kotlin.data.model.network

interface BasePageListResponse<T> {
    var page: Int
    var results: List<T>
}