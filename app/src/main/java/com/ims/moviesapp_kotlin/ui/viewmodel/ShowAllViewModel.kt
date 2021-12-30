package com.ims.moviesapp_kotlin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.ims.moviesapp_kotlin.data.db.repository.MovieRepository
import com.ims.moviesapp_kotlin.data.model.Event
import com.ims.moviesapp_kotlin.data.model.GoToMovie
import com.ims.moviesapp_kotlin.data.model.MovieListType
import com.ims.moviesapp_kotlin.data.model.entity.Movie
import com.ims.moviesapp_kotlin.ui.BaseViewModel
import com.ims.moviesapp_kotlin.util.extension.appendList
import com.ims.moviesapp_kotlin.util.extension.liveDataBlockScope

class ShowAllViewModel(movieListType: MovieListType) : BaseViewModel(), GoToMovie {
    override val goToMovieDetailsEvent: MutableLiveData<Event<Movie>> = MutableLiveData()

    private val movieRepository = MovieRepository()
    private val moviePage = MutableLiveData<Int>().apply { value = 1 }
    private val loadedMovieList: LiveData<List<Movie>>
    val movieList = MediatorLiveData<MutableList<Movie>>()

    init {
        loadedMovieList = when (movieListType){
            MovieListType.POPULAR ->{
                moviePage.switchMap {
                    liveDataBlockScope {
                        movieRepository.loadPopularList(it){
                            mSnackBarText.postValue(Event(it))
                        }
                    }
                }
            }
            MovieListType.IN_THEATERS ->{
                moviePage.switchMap {
                    liveDataBlockScope {
                        movieRepository.loadInTheatersList(it){
                            mSnackBarText.postValue(Event(it))
                        }
                    }
                }
            }
            else ->{
                moviePage.switchMap {
                    liveDataBlockScope {
                        movieRepository.loadUpcomingList(it){
                            mSnackBarText.postValue(Event(it))
                        }
                    }
                }
            }
        }
        movieList.addSource(loadedMovieList){
            it?.let {
                list -> movieList.appendList(list)
            }
        }
    }
    fun loadMoreMovies() {
        moviePage.value = moviePage.value?.plus(1)
    }

}