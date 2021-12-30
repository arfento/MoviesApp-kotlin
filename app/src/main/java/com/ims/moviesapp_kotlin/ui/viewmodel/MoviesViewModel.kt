package com.ims.moviesapp_kotlin.ui.viewmodel

import androidx.lifecycle.*
import com.ims.moviesapp_kotlin.data.db.repository.MovieRepository
import com.ims.moviesapp_kotlin.data.model.Event
import com.ims.moviesapp_kotlin.data.model.GoToMovie
import com.ims.moviesapp_kotlin.data.model.entity.Movie
import com.ims.moviesapp_kotlin.ui.BaseViewModel
import com.ims.moviesapp_kotlin.util.extension.appendList
import com.ims.moviesapp_kotlin.util.extension.liveDataBlockScope

class MoviesViewModel : BaseViewModel(), GoToMovie {

    private val movieRepository = MovieRepository()
    private val loadedMovies: LiveData<List<Movie>>
    private val moviesPage = MutableLiveData<Int>().apply { value = 1 }
    override val goToMovieDetailsEvent: MutableLiveData<Event<Movie>> = MutableLiveData()

    val movieList = MediatorLiveData<MutableList<Movie>>()

    init {
        loadedMovies = moviesPage.switchMap {
            liveDataBlockScope {
                movieRepository.loadDiscoverList(it) {
                    mSnackBarText.postValue(Event(it))
                }
            }
        }
        movieList.addSource(loadedMovies) {
            it?.let { list ->
                movieList.appendList(list)
            }
        }
    }
    fun loadMoreMovies(){
        moviesPage.value = moviesPage.value?.plus(1)
    }


}