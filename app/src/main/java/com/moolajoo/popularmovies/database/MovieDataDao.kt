package com.moolajoo.popularmovies.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 * Created by joaopaulotargino on 2018-03-05.
 */
@Dao
interface MovieDataDao {

    @Query("SELECT * from movieData")
    fun getAll(): List<MovieData>

    @Insert(onConflict = REPLACE)
    fun insert(movieData: MovieData)


    @Delete()
    fun delete(movieData: MovieData)

    @Query("DELETE from movieData")
    fun deleteAll()
}