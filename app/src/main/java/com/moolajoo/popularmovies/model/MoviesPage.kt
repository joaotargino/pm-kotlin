package com.moolajoo.popularmovies.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

/**
 * Created by joaopaulotargino on 2018-03-02.
 */
class MoviesPage() : Parcelable {

    @Expose
    var movieList: ArrayList<Movie>? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MoviesPage> {
        override fun createFromParcel(parcel: Parcel): MoviesPage {
            return MoviesPage(parcel)
        }

        override fun newArray(size: Int): Array<MoviesPage?> {
            return arrayOfNulls(size)
        }
    }
}