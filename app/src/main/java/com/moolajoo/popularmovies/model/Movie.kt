package com.moolajoo.popularmovies.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by joaopaulotargino on 2018-03-02.
 */
class Movie(val title: String, val original_title: String, val vote_average: Double, val vote_count: Int, val id: Int
            , val popularity: Double, val poster_path: String, val backdrop_path: String,
            val overview: String, val release_date: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
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
        dest?.writeString(original_title)
        dest?.writeDouble(vote_average)
        dest?.writeInt(vote_count)
        dest?.writeInt(id)
        dest?.writeDouble(popularity)
        dest?.writeString(poster_path)
        dest?.writeString(backdrop_path)
        dest?.writeString(overview)
        dest?.writeString(release_date)


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

