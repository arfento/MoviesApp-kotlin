package com.ims.moviesapp_kotlin.data.db.repository

import androidx.lifecycle.MutableLiveData
import com.ims.moviesapp_kotlin.data.db.remote.TheMovieDatabaseAPI
import com.ims.moviesapp_kotlin.data.model.entity.Credit
import com.ims.moviesapp_kotlin.data.model.entity.Image
import com.ims.moviesapp_kotlin.data.model.entity.Person
import com.ims.moviesapp_kotlin.util.ServiceBuilder

class PersonRepository : BaseRepository() {
    private val peopleService =
        ServiceBuilder.buildService(TheMovieDatabaseAPI.PeopleService::class.java)

    suspend fun loadDetails(id: Int, errorText: (String) -> Unit) =
        loadCall({ peopleService.fetchDetails(id) }, MutableLiveData<Person>(), errorText)

    suspend fun loadCredits(id: Int, errorText: (String) -> Unit) =
        loadListCall(
            { peopleService.fetchCredits(id) },
            MutableLiveData<List<Credit>>(),
            errorText
        )

    suspend fun loadImages(id: Int, errorText: (String) -> Unit) =
        loadListCall({ peopleService.fetchImages(id) }, MutableLiveData<List<Image>>(), errorText)
}