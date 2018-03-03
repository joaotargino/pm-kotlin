package com.moolajoo.popularmovies.networking

import com.moolajoo.popularmovies.model.Movie
import com.moolajoo.popularmovies.model.MovieResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by joaopaulotargino on 2018-03-02.
 */
interface TMDBApiService {

    @GET("movie/{sort}?api_key=")
    abstract fun getMovieList(@Path("sort") sort: String, @Query("api_key") apiKey: String): Observable<MovieResponse>

    @GET("movie/{id}?api_key=")
    abstract fun getMovie(@Path("id") id: String, @Query("api_key") apiKey: String): Observable<Movie>

}