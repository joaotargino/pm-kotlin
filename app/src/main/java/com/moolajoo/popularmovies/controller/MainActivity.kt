package com.moolajoo.popularmovies.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import com.moolajoo.popularmovies.R
import com.moolajoo.popularmovies.adapters.GridMoviesAdapter
import com.moolajoo.popularmovies.database.DbWorkerThread
import com.moolajoo.popularmovies.database.MovieDatabase
import com.moolajoo.popularmovies.model.Movie
import com.moolajoo.popularmovies.model.MovieResponse
import com.moolajoo.popularmovies.networking.ApiClient
import com.moolajoo.popularmovies.util.API_KEY
import com.moolajoo.popularmovies.util.EXTRA_MOVIE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val tmdbApiServe by lazy {
        ApiClient.create()
    }
    private var disposable: Disposable? = null

    private var mMovieList: List<Movie>? = null
    private var favorites: ArrayList<Movie>? = arrayListOf()

    lateinit var adapter: GridMoviesAdapter

    private lateinit var currentOrder: String
    private var movieResponse: MovieResponse? = null


    private var mDB: MovieDatabase? = null
    private lateinit var mDbWorkerThread: DbWorkerThread
    private val mUiHandler = Handler()

    private var loadFinished = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            currentOrder = getString(R.string.POPULAR)
            fetchMovies(currentOrder)
        } else {
            currentOrder = savedInstanceState.getString(getString(R.string.MOVIES_CURRENT_ORDER))
            movieResponse = savedInstanceState.getParcelable(getString(R.string.MOVIES_RESPONSE))
            mMovieList = movieResponse?.data
            populateMoviesList()
        }

        fab_popularity.setOnClickListener(this)
        fab_rating.setOnClickListener(this)
        fab_favorites.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(getString(R.string.MOVIES_CURRENT_ORDER), currentOrder)
        outState?.putParcelable(getString(R.string.MOVIES_RESPONSE), movieResponse)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        movieResponse = savedInstanceState!!.getParcelable(getString(R.string.MOVIES_RESPONSE))

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            MovieDatabase.destroyInstance()
            mDbWorkerThread.quit()
        } catch (e: Exception) {
            println(e.message)
        }

    }

    override fun onClick(v: View?) {
        when (v) {
            fab_popularity -> {
                currentOrder = getString(R.string.POPULAR)
                fetchMovies(currentOrder)
            }
            fab_rating -> {
                currentOrder = getString(R.string.TOP_RATED)
                fetchMovies(currentOrder)
            }
            fab_favorites -> {
                currentOrder = getString(R.string.FAVORITES)
                //access db
                fetchDataFromDb()
            }
        }
        fab_orderby_menu.collapse()
    }

    private fun fetchMovieByID(id: String, loaded: Int) {
        disposable =
                tmdbApiServe.getMovie(id, API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    favorites!!.add(result)

                                    if (favorites!!.size == loaded) {
                                        mMovieList = favorites
                                        populateMoviesList()
                                    }

                                },
                                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                        )

    }

    private fun fetchMovies(sort: String) {

        disposable =
                tmdbApiServe.getMovieList(sort, API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    movieResponse = result
                                    mMovieList = movieResponse?.data
                                    populateMoviesList()
                                },
                                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                        )
    }


    private fun populateMoviesList() {

        adapter = GridMoviesAdapter(this, mMovieList!!) { movieItem ->
            val movieIntent = Intent(this, DetailActivity::class.java)
            movieIntent.putExtra(EXTRA_MOVIE, movieItem)
            startActivity(movieIntent)
        }
        recyclerView.adapter = adapter

        val layoutManager = GridLayoutManager(this, numberOfColumns())
        recyclerView.layoutManager = layoutManager
//        recyclerView.layoutManager.smoothScrollToPosition(recyclerView, null, viewPosition) //TODO scroll on restore
        recyclerView.setHasFixedSize(true)

    }


    private fun numberOfColumns(): Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthDivider = 300
        val width = displayMetrics.widthPixels
        val nColumns = width / widthDivider
        return if (nColumns < 2) 2 else nColumns
    }


    private fun fetchDataFromDb() {


        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        mDB = MovieDatabase.getInstance(this)

        val task = Runnable {
            val movieData =
                    mDB?.movieDataDao()?.getAll()
            mUiHandler.post({
                if (movieData == null || movieData?.size == 0) {
                    Snackbar.make(recyclerView, getString(R.string.FAVORITES_EMPTY), Snackbar.LENGTH_SHORT).show()
                } else {
                    for (m in movieData.iterator()) {
                        fetchMovieByID(m.movieId.toString(), movieData.size)
                    }
                }
            })

        }

        try {
            mDbWorkerThread.postTask(task)

        } catch (e: Exception) {
            println(e.message)

            //catching this on the second time. wtf
            //[lateinit property mWorkerHandler has not been initialized]
        }
    }


}
