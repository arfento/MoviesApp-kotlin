package com.ims.moviesapp_kotlin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ims.moviesapp_kotlin.data.db.repository.MovieRepository
import com.ims.moviesapp_kotlin.data.model.Event
import com.ims.moviesapp_kotlin.data.model.GoToCast
import com.ims.moviesapp_kotlin.data.model.GoToVideo
import com.ims.moviesapp_kotlin.data.model.entity.Cast
import com.ims.moviesapp_kotlin.data.model.entity.Movie
import com.ims.moviesapp_kotlin.data.model.entity.Video
import com.ims.moviesapp_kotlin.ui.BaseViewModel
import com.ims.moviesapp_kotlin.util.extension.liveDataBlockScope

class MovieDetailsViewModel(
    movieId: Int
) : BaseViewModel(), GoToCast, GoToVideo {
    override val goToCastDetailsEvent: MutableLiveData<Event<Cast>> = MutableLiveData()
    override val goToVideoEvent: MutableLiveData<Event<Video>> = MutableLiveData()

    private val movieRepository = MovieRepository()
    val movie: LiveData<Movie>
    val videoList : LiveData<List<Video>>
    val castList : LiveData<List<Cast>>

    init {

        movie = liveDataBlockScope {
            movieRepository.loadDetails(movieId){
                mSnackBarText.postValue(Event(it))

            }
        }

        videoList = liveDataBlockScope {
            movieRepository.loadVideos(movieId){
                mSnackBarText.postValue(Event(it))
            }
        }

        castList = liveDataBlockScope {
            movieRepository.loadCredits(movieId){
                mSnackBarText.postValue(Event(it))
            }
        }
    }


}