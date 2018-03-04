package com.moolajoo.popularmovies.model


import com.google.gson.annotations.SerializedName

/**
 * Created by joaopaulotargino on 2018-03-02.
 */
class MovieResponse {

    @SerializedName("results")
    var data: List<Movie>? = null

}