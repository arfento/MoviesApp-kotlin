package com.ims.moviesapp_kotlin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ims.moviesapp_kotlin.data.db.repository.TvRepository
import com.ims.moviesapp_kotlin.data.model.Event
import com.ims.moviesapp_kotlin.data.model.GoToCast
import com.ims.moviesapp_kotlin.data.model.GoToVideo
import com.ims.moviesapp_kotlin.data.model.entity.Cast
import com.ims.moviesapp_kotlin.data.model.entity.TvShowDetails
import com.ims.moviesapp_kotlin.data.model.entity.Video
import com.ims.moviesapp_kotlin.ui.BaseViewModel
import com.ims.moviesapp_kotlin.util.extension.liveDataBlockScope

class TVShowDetailsViewModel(tvShowId: Int) : BaseViewModel(), GoToVideo, GoToCast {
    override val goToCastDetailsEvent: MutableLiveData<Event<Cast>> = MutableLiveData()
    override val goToVideoEvent: MutableLiveData<Event<Video>> = MutableLiveData()

    private val tvShowRepository = TvRepository()
    val tvShow : LiveData<TvShowDetails>
    val videoList: LiveData<List<Video>>
    val castList: LiveData<List<Cast>>

    init {
        tvShow = liveDataBlockScope {
            tvShowRepository.loadDetails(tvShowId){
                mSnackBarText.postValue(Event(it))
            }
        }
        videoList = liveDataBlockScope {
            tvShowRepository.loadVideos(tvShowId){
                mSnackBarText.postValue(Event(it))
            }
        }
        castList = liveDataBlockScope {
            tvShowRepository.loadCredits(tvShowId){
                mSnackBarText.postValue(Event(it))
            }
        }
    }

}