package com.moolajoo.popularmovies.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by joaopaulotargino on 2018-03-02.
 */
class MovieResponse() : Parcelable {

    @SerializedName("results")
    var data: List<Movie>? = null

    constructor(parcel: Parcel) : this() {
        data = parcel.createTypedArrayList(Movie)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieResponse> {
        override fun createFromParcel(parcel: Parcel): MovieResponse {
            return MovieResponse(parcel)
        }

        override fun newArray(size: Int): Array<MovieResponse?> {
            return arrayOfNulls(size)
        }
    }

}