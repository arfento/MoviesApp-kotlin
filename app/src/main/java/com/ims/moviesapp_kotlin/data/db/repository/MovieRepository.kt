package com.ims.moviesapp_kotlin.data.db.repository

import androidx.lifecycle.MutableLiveData
import com.ims.moviesapp_kotlin.data.db.remote.TheMovieDatabaseAPI
import com.ims.moviesapp_kotlin.data.model.entity.Cast
import com.ims.moviesapp_kotlin.data.model.entity.Genre
import com.ims.moviesapp_kotlin.data.model.entity.Movie
import com.ims.moviesapp_kotlin.data.model.entity.Video
import com.ims.moviesapp_kotlin.util.ServiceBuilder

class MovieRepository : BaseRepository() {
    private val movieService =
        ServiceBuilder.buildService(TheMovieDatabaseAPI.MovieService::class.java)

    suspend fun loadPopularList(page: Int, errorText: (String) -> Unit) =
        loadPageListCall(
            { movieService.fetchPopularList(page) },
            MutableLiveData<List<Movie>>(),
            errorText
        )


    suspend fun loadInTheatersList(page: Int, errorText: (String) -> Unit) =
        loadPageListCall(
            { movieService.fetchInTheatersList(page) },
            MutableLiveData<List<Movie>>(),
            errorText
        )
    suspend fun loadUpcomingList(page: Int, errorText: (String) -> Unit) =
        loadPageListCall(
            { movieService.fetchUpcomingList(page) },
            MutableLiveData<List<Movie>>(),
            errorText
        )

    suspend fun loadDiscoverList(page: Int, errorText: (String) -> Unit) =
        loadPageListCall(
            { movieService.fetchDiscoverList(page) },
            MutableLiveData<List<Movie>>(),
            errorText
        )

    suspend fun loadGenreList(errorText: (String) -> Unit) = loadListCall(
        { movieService.fetchMovieGenreList() },
        MutableLiveData<List<Genre>>(),
        errorText
    )

    suspend fun loadDetails(id: Int, errorText: (String) -> Unit) =
        loadCall(
            { movieService.fetchDetails(id) },
            MutableLiveData<Movie>(),
            errorText
        )

    suspend fun loadCredits(id: Int, errorText: (String) -> Unit) =
        loadListCall(
            { movieService.fetchCredits(id) },
            MutableLiveData<List<Cast>>(),
            errorText
        )

    suspend fun loadVideos(id: Int, errorText: (String) -> Unit) =
        loadListCall(
            { movieService.fetchVideos(id) },
            MutableLiveData<List<Video>>(),
            errorText
        )
}