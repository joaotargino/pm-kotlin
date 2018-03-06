package com.moolajoo.popularmovies.database

import android.content.Context
import android.os.Handler

/**
 * Created by joaopaulotargino on 2018-03-06.
 */

class DBHelper {

    private var mDB: MovieDatabase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread
    private var mUiHandler = Handler()

    fun fetchDataFromDb(context: Context) {


        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        mDB = MovieDatabase.getInstance(context)

        val task = Runnable {
            val movieData =
                    mDB?.movieDataDao()?.getAll()
            mUiHandler.post({
                if (movieData == null || movieData?.size == 0) {
                    println("no data")
                } else {
                    for (m in movieData.iterator()) {
                        println(m.title)
                    }
                }
            })
        }
        try {
            mDbWorkerThread.postTask(task)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun destroy() {
        println("helper destroyed")
        MovieDatabase.destroyInstance()
        mDbWorkerThread.quit()
    }
}