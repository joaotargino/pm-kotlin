package com.moolajoo.popularmovies.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by joaopaulotargino on 2018-03-05.
 */

@Entity(tableName = "movieData")
data class MovieData(@PrimaryKey(autoGenerate = true) var id: Long?,


                     @ColumnInfo(name = "title") var title: String,
                     @ColumnInfo(name = "original_title") var originalTitle: String,
                     @ColumnInfo(name = "vote_average") var voteAverage: Double,
                     @ColumnInfo(name = "vote_count") var voteCount: Int,
                     @ColumnInfo(name = "movie_id") var movieId: Int,
                     @ColumnInfo(name = "popularity") var popularity: Double,
                     @ColumnInfo(name = "poster_path") var posterPath: String,
                     @ColumnInfo(name = "backdrop_path") var backdropPath: String,
                     @ColumnInfo(name = "overview") var overview: String,
                     @ColumnInfo(name = "release_date") var releaseDate: String

) {
    constructor() : this(null, "", "", 0.0, 0, 0,
            0.0, "", "", "", "")


}

//val title: String, val original_title: String, val vote_average: Double, val vote_count: Int, val id: Int
//, val popularity: Double, val poster_path: String, val backdrop_path: String,
//val overview: String, val release_date: String