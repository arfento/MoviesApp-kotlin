package com.ims.moviesapp_kotlin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ims.moviesapp_kotlin.data.db.repository.PersonRepository
import com.ims.moviesapp_kotlin.data.model.Event
import com.ims.moviesapp_kotlin.data.model.GoToCredit
import com.ims.moviesapp_kotlin.data.model.GoToImage
import com.ims.moviesapp_kotlin.data.model.entity.Credit
import com.ims.moviesapp_kotlin.data.model.entity.Image
import com.ims.moviesapp_kotlin.data.model.entity.Person
import com.ims.moviesapp_kotlin.ui.BaseViewModel
import com.ims.moviesapp_kotlin.util.extension.liveDataBlockScope

class PersonDetailsViewModel(personId: Int) : BaseViewModel(), GoToImage, GoToCredit {

    private val personRepository = PersonRepository()
    val person: LiveData<Person>
    val imageList: LiveData<List<Image>>
    val creditList: LiveData<List<Credit>>

    override val goToImageEvent: MutableLiveData<Event<Image>> = MutableLiveData()
    override val goToCreditEvent: MutableLiveData<Event<Credit>> = MutableLiveData()

    init {
        person = liveDataBlockScope {
            personRepository.loadDetails(personId) { mSnackBarText.postValue(Event(it)) }
        }

        imageList = liveDataBlockScope {
            personRepository.loadImages(personId) { mSnackBarText.postValue(Event(it)) }
        }

        creditList = liveDataBlockScope {
            personRepository.loadCredits(personId) { mSnackBarText.postValue(Event(it)) }
        }
    }
}