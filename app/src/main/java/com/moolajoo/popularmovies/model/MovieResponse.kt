package com.moolajoo.popularmovies.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by joaopaulotargino on 2018-03-02.
 */
class MovieResponse {

    @SerializedName("results")
    var data: List<MovieData>? = null

    inner class MovieData : Serializable {
        @SerializedName("vote_count")
        val vote_count: Int? = null

        @SerializedName("id")
        val idMovie: Int? = null

        @SerializedName("vote_average")
        val vote_average: Double? = null

        @SerializedName("title")
        val movieTitle: String? = null

        @SerializedName("original_title")
        val originalTitle: String? = null

        @SerializedName("popularity")
        val popularity: Double? = null

        @SerializedName("poster_path")
        val poster_path: String? = null

        @SerializedName("backdrop_path")
        val backdrop_path: String? = null

        @SerializedName("overview")
        val overview: String? = null

        @SerializedName("release_date")
        val release_date: String? = null

    }


}