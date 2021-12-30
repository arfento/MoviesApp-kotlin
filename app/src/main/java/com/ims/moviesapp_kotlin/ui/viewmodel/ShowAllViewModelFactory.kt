package com.ims.moviesapp_kotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ims.moviesapp_kotlin.data.model.MovieListType

class ShowAllViewModelFactory(private val movieListType: MovieListType) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ShowAllViewModel(movieListType) as T
    }
}