package com.moolajoo.popularmovies.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.moolajoo.popularmovies.R
import com.moolajoo.popularmovies.adapters.GridMoviesAdapter
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

    lateinit var adapter: GridMoviesAdapter

    private lateinit var currentOrder: String
    private var movieResponse: MovieResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            currentOrder = getString(R.string.POPULAR)
            fetchMovies(currentOrder)
        } else {
            currentOrder = savedInstanceState!!.getString(getString(R.string.MOVIES_CURRENT_ORDER))
            populateMoviesList(savedInstanceState!!.getParcelable(getString(R.string.MOVIES_RESPONSE)))
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
                Toast.makeText(this, "TODO - open db", LENGTH_SHORT).show()
            }
        }
        fab_orderby_menu.collapse()
    }

    private fun fetchMovies(sort: String) {

        disposable =
                tmdbApiServe.getMovieList(sort, API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    movieResponse = result
                                    populateMoviesList(movieResponse)
                                },
                                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                        )
    }


    private fun populateMoviesList(movieResponse: MovieResponse?) {
        mMovieList = movieResponse?.data

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
        // You can change this divider to adjust the size of the poster
        val widthDivider = 300
        val width = displayMetrics.widthPixels
        val nColumns = width / widthDivider
        return if (nColumns < 2) 2 else nColumns
    }

}
