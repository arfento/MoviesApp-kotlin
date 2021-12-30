package com.ims.moviesapp_kotlin.ui.viewmodel

import androidx.lifecycle.*
import com.ims.moviesapp_kotlin.data.db.repository.TvRepository
import com.ims.moviesapp_kotlin.data.model.Event
import com.ims.moviesapp_kotlin.data.model.GoToTvShow
import com.ims.moviesapp_kotlin.data.model.entity.TvShow
import com.ims.moviesapp_kotlin.ui.BaseViewModel
import com.ims.moviesapp_kotlin.util.extension.appendList
import com.ims.moviesapp_kotlin.util.extension.liveDataBlockScope

class TvShowsViewModel : BaseViewModel(), GoToTvShow{
    override val goToTvShowEvent: MutableLiveData<Event<TvShow>> = MutableLiveData()
    private val tvShowRepository = TvRepository()
    private val loadedTvShowList: LiveData<List<TvShow>>
    private val tvShowsPage = MutableLiveData<Int>().apply {  value = 1 }

    val tvShowList= MediatorLiveData<MutableList<TvShow>>()

    init {
        loadedTvShowList = tvShowsPage.switchMap{
            liveDataBlockScope {
                tvShowRepository.loadDiscoverList(it){
                    mSnackBarText.postValue(Event(it))
                }
            }
        }
        tvShowList.addSource(loadedTvShowList){
            it?.let {
                list -> tvShowList.appendList(list)
            }
        }
    }
    fun loadMoreTvShows() {
        tvShowsPage.value = tvShowsPage.value?.plus(1)
    }
}
