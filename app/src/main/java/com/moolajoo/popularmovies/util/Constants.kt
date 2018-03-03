package com.moolajoo.popularmovies.util

import com.moolajoo.popularmovies.BuildConfig

/**
 * Created by joaopaulotargino on 2018-03-02.
 */

const val BASE_URL = "http://api.themoviedb.org/3/"
const val POSTER_BASE_URL = "http://image.tmdb.org/t/p/w300/"
const val BACKDROP_BASE_URL = "http://image.tmdb.org/t/p/w300/"
const val API_KEY: String = BuildConfig.TMDB_API_KEY

const val EXTRA_MOVIE_POSTER = "movie_poster"
const val EXTRA_MOVIE_TITLE = "movie_title"