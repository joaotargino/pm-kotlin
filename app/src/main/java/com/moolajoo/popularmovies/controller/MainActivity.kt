package com.moolajoo.popularmovies.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.DisplayMetrics
import android.widget.Toast
import com.moolajoo.popularmovies.R
import com.moolajoo.popularmovies.adapters.GridMoviesAdapter
import com.moolajoo.popularmovies.model.MovieResponse
import com.moolajoo.popularmovies.networking.ApiClient
import com.moolajoo.popularmovies.util.API_KEY
import com.moolajoo.popularmovies.util.EXTRA_MOVIE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val tmdbApiServe by lazy {
        ApiClient.create()
    }
    var disposable: Disposable? = null

    var mMovieList: List<MovieResponse.MovieData>? = null

    lateinit var adapter: GridMoviesAdapter
    private val viewPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        fetchMovies("popular")
        fetchMovies("top_rated")

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun fetchMovies(sort: String) {

        disposable =
                tmdbApiServe.getMovieList(sort, API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    populateMoviesList(result.data)
                                },
                                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                        )
    }

    private fun populateMoviesList(movieList: List<MovieResponse.MovieData>?) {
        mMovieList = movieList
//        for (movie in mMovieList!!.iterator()) {
//            println(movie.movieTitle)
//        }

        adapter = GridMoviesAdapter(this, mMovieList!!) { movieItem ->
            val movieIntent = Intent(this, DetailActivity::class.java)
            movieIntent.putExtra(EXTRA_MOVIE, movieItem)

            startActivity(movieIntent)
            Toast.makeText(this, movieItem.movieTitle, Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        val layoutManager = GridLayoutManager(this, numberOfColumns())
        recyclerView.layoutManager = layoutManager
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
