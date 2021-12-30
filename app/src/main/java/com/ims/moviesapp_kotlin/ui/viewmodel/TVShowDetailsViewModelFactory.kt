package com.ims.moviesapp_kotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TVShowDetailsViewModelFactory(private val tvShowId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TVShowDetailsViewModel(tvShowId) as T
    }
}