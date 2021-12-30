package com.ims.moviesapp_kotlin.ui.binding

import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import com.ims.moviesapp_kotlin.data.db.remote.TheMovieDatabaseAPI
import com.ims.moviesapp_kotlin.data.model.entity.Movie

@BindingAdapter("bind_rating_bar", "bind_rating_stars")
fun RatingBar.bindRatingBar(movie: Movie?, stars: Int) {
    movie?.let { this.rating = stars * ((it.voteAverage / TheMovieDatabaseAPI.MAX_RATING)) }
}

@BindingAdapter("bind_rating_bar_tv_show", "bind_rating_stars")
fun RatingBar.bindRatingBar(voteAverage: Float, stars: Int) {
    this.rating = stars * ((voteAverage / TheMovieDatabaseAPI.MAX_RATING))
}