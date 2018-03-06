package com.moolajoo.popularmovies.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by joaopaulotargino on 2018-03-05.
 */
@Database(entities = arrayOf(MovieData::class), version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDataDao(): MovieDataDao

    companion object {
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase? {
            if (INSTANCE == null) {
                synchronized(MovieDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase::class.java, "movies.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}