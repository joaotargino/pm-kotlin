package com.moolajoo.popularmovies.controller

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.moolajoo.popularmovies.R
import com.moolajoo.popularmovies.database.DbWorkerThread
import com.moolajoo.popularmovies.database.MovieData
import com.moolajoo.popularmovies.database.MovieDatabase
import com.moolajoo.popularmovies.model.Movie
import com.moolajoo.popularmovies.util.BACKDROP_BASE_URL
import com.moolajoo.popularmovies.util.EXTRA_MOVIE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    private var mDB: MovieDatabase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread
    private val mUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val movieObject = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        //database
        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        tvTitle.text = movieObject.title
//        supportActionBar!!.title = movieObject.title
        collapsing_toolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
        collapsing_toolbar.title = movieObject.title

//        val barTitle = "$movieExtrasTitle $movieExtrasRating"
//        title = barTitle

        //collapsed bar
        Picasso.with(this)
                .load(BACKDROP_BASE_URL
                        + movieObject.poster_path)
                .into(ivPoster)

        //card 1
        tvOverview.text = movieObject.overview

        //card 2
        Picasso.with(this)
                .load(BACKDROP_BASE_URL
                        + movieObject.backdrop_path)
                .into(ivBackdrop)
        tvOriginalTitle.text = movieObject.original_title
        val releaseText = "${getString(R.string.release_text)} ${movieObject.release_date}"
        tvReleaseDate.text = releaseText
        val ratingText = "${getString(R.string.rating_text)} ${movieObject.vote_average}"
        tvRating.text = ratingText


        //database
        mDB = MovieDatabase.getInstance(this)
        try {
            fetchDataFromDb()
        } catch (e: UninitializedPropertyAccessException) {
//            mDbWorkerThread.start()
            fetchDataFromDb()
        } catch (e: IllegalThreadStateException) {
            println(e.message)
        }

        //fab favorite
        fab.setOnClickListener {
            var movieData = MovieData()
            movieData.movieId = movieObject.id
            movieData.title = movieObject.title
            insertDataInDb(movieData)
//            deleteDataInDb(movieData)
            //            if (data?.contains())
        }
    }

    private fun fetchDataFromDb() {
        val task = Runnable {
            val movieData =
                    mDB?.movieDataDao()?.getAll()
            mUiHandler.post({
                if (movieData == null || movieData?.size == 0) {
                    println("no data")
                } else {
                    for (m in movieData.iterator())
                        println(m.title)
                }
            })
        }
        try {
            mDbWorkerThread.postTask(task)
        } catch (e: Exception) {
            println(e.message)
        }
    }


    private fun insertDataInDb(movieData: MovieData) {
        val task = Runnable { mDB?.movieDataDao()?.insert(movieData) }
        mDbWorkerThread.postTask(task)
    }

    private fun deleteDataInDb(movieData: MovieData) {
        val task = Runnable {
//            mDB?.movieDataDao()?.deleteAll()
            mDB?.movieDataDao()?.delete(movieData)
        }
        mDbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        println("destroyed")
        MovieDatabase.destroyInstance()
        mDbWorkerThread.quit()
        super.onDestroy()
    }

}
