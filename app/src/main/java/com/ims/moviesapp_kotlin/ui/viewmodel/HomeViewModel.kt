package com.ims.moviesapp_kotlin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.ims.moviesapp_kotlin.data.db.repository.MovieRepository
import com.ims.moviesapp_kotlin.data.model.CombinedLiveData
import com.ims.moviesapp_kotlin.data.model.Event
import com.ims.moviesapp_kotlin.data.model.GoToMovie
import com.ims.moviesapp_kotlin.data.model.MovieListType
import com.ims.moviesapp_kotlin.data.model.entity.Genre
import com.ims.moviesapp_kotlin.data.model.entity.Movie
import com.ims.moviesapp_kotlin.ui.BaseViewModel
import com.ims.moviesapp_kotlin.util.extension.appendList
import com.ims.moviesapp_kotlin.util.extension.liveDataBlockScope

class HomeViewModel : BaseViewModel(), GoToMovie {
    override val goToMovieDetailsEvent: MutableLiveData<Event<Movie>> = MutableLiveData()

    private val movieRepository = MovieRepository()
    private val combinedLiveData: CombinedLiveData<Movie, List<Genre>>
    private val genres: LiveData<List<Genre>>

    private val loadedPopularMovieList: LiveData<List<Movie>>
    private val loadedInTheatersMovieList: LiveData<List<Movie>>
    private val loadedUpcomingMovieList: LiveData<List<Movie>>

    private val popularPage = MutableLiveData<Int>().apply { value = 1 }
    private val inTheatersPage = MutableLiveData<Int>().apply { value = 1 }
    private val upcomingPage = MutableLiveData<Int>().apply { value = 1 }

    private val _goToShowAllEvent = MutableLiveData<Event<MovieListType>>()

    val popularMovieList = MediatorLiveData<MutableList<Movie>>()
    val inTheatersMovieList = MediatorLiveData<MutableList<Movie>>()
    val upcomingMovieList = MediatorLiveData<MutableList<Movie>>()

    val highlightedMovie = MediatorLiveData<Movie>()
    val highlightedMovieGenreOne = MediatorLiveData<Genre>()
    val highlightedMovieGenreTwo = MediatorLiveData<Genre>()

    val goToShowAllEvent: LiveData<Event<MovieListType>> = _goToShowAllEvent

    init {
        loadedPopularMovieList = popularPage.switchMap {
            liveDataBlockScope {
                movieRepository.loadPopularList(it) {
                    mSnackBarText.postValue(Event(it))
                }
            }
        }
        loadedInTheatersMovieList = inTheatersPage.switchMap {
            liveDataBlockScope {
                movieRepository.loadInTheatersList(it) {
                    mSnackBarText.postValue(Event(it))
                }
            }
        }

        loadedUpcomingMovieList = upcomingPage.switchMap {
            liveDataBlockScope {
                movieRepository.loadUpcomingList(it) {
                    mSnackBarText.postValue(Event(it))
                }
            }
        }

        genres =
            liveDataBlockScope { movieRepository.loadGenreList { mSnackBarText.postValue(Event(it)) } }

        combinedLiveData =
            CombinedLiveData<Movie, List<Genre>>(highlightedMovie, genres)

        popularMovieList.addSource(loadedPopularMovieList) {
            it?.let { list -> popularMovieList.appendList(list) }
        }

        inTheatersMovieList.addSource(loadedInTheatersMovieList) {
            it?.let { list -> inTheatersMovieList.appendList(list) }
        }

        upcomingMovieList.addSource(loadedUpcomingMovieList) {
            it?.let { list -> upcomingMovieList.appendList(list) }
        }

        highlightedMovie.addSource(loadedPopularMovieList) {
            if (highlightedMovie.value == null) {
                highlightedMovie.value = it?.first()
            }
        }
        highlightedMovieGenreOne.addSource(combinedLiveData) { result ->
            result?.first?.genreIds?.let {
                if (it.isEmpty()) return@let
                val genreId = it[0]
                highlightedMovieGenreOne.value =
                    result.second?.find { genre -> genre.id == genreId }
            }
        }

        highlightedMovieGenreTwo.addSource(combinedLiveData) { result ->
            result?.first?.genreIds?.let {
                if (it.size < 2) return@let
                val genreId = it[1]
                highlightedMovieGenreTwo.value =
                    result.second?.find { genre -> genre.id == genreId }
            }
        }
    }
    fun loadMorePopular() {
        popularPage.value = popularPage.value?.plus(1)
    }

    fun loadMoreInTheaters() {
        inTheatersPage.value = inTheatersPage.value?.plus(1)
    }

    fun loadMoreUpcoming() {
        upcomingPage.value = upcomingPage.value?.plus(1)
    }

    fun goToShowAllPressed(movieListType: MovieListType) {
        _goToShowAllEvent.value = Event(movieListType)
    }

    override fun goTo(movie: Movie) {
        goToMovieDetailsEvent.value = Event(movie)
    }
}