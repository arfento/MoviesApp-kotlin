package com.ims.moviesapp_kotlin.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PersonDetailsViewModelFactory(private val personId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PersonDetailsViewModel(personId) as T
    }
}
