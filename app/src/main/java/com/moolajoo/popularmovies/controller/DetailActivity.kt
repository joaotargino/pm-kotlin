package com.moolajoo.popularmovies.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.moolajoo.popularmovies.R
import com.moolajoo.popularmovies.model.MovieResponse
import com.moolajoo.popularmovies.util.BACKDROP_BASE_URL
import com.moolajoo.popularmovies.util.EXTRA_MOVIE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val movieObject = intent.getSerializableExtra(EXTRA_MOVIE) as MovieResponse.MovieData


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        tvTitle.text = movieObject.movieTitle
        supportActionBar!!.title = movieObject.movieTitle
//        collapsing_toolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))


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
        tvOriginalTitle.text = movieObject.originalTitle
        val releaseText = "${getString(R.string.release_text)} ${movieObject.release_date}"
        tvReleaseDate.text = releaseText
        val ratingText = "${getString(R.string.rating_text)} ${movieObject.vote_average}"
        tvRating.text = ratingText
    }


}
