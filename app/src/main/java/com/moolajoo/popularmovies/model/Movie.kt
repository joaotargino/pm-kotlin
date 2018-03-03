package com.moolajoo.popularmovies.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by joaopaulotargino on 2018-03-02.
 */
class Movie(val title: String, val voteAverage: Double, val voteCount: Int, val idMovie: Int
            , val popularity: Double, val posterPath: String, val posterBackdrop: String,
            val overview: String, val releaseDate: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(title)
        dest?.writeString(overview)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {

            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}

