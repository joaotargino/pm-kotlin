package com.moolajoo.popularmovies.controller

import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.view.View
import com.moolajoo.popularmovies.R
import com.moolajoo.popularmovies.util.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initActivityTransitions()

        val movieExtrasPoster = intent.getStringExtra(EXTRA_MOVIE_POSTER)
        val movieExtrasTitle = intent.getStringExtra(EXTRA_MOVIE_TITLE)
        val movieExtrasBackdrop = intent.getStringExtra(EXTRA_MOVIE_BACKDROP_POSTER)
        val movieExtrasOverview = intent.getStringExtra(EXTRA_MOVIE_OVERVIEW)
        val movieExtrasReleaseDate = intent.getStringExtra(EXTRA_MOVIE_RELEASE)
        val movieExtrasRating = intent.getDoubleExtra(EXTRA_MOVIE_RATING, 0.0)


        ViewCompat.setTransitionName(findViewById<View>(R.id.app_bar_layout), EXTRA_MOVIE_BACKDROP_POSTER)
        supportPostponeEnterTransition()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        tvTitle.text = movieExtrasTitle
        supportActionBar!!.title = movieExtrasTitle
//        collapsing_toolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))


//        val barTitle = "$movieExtrasTitle $movieExtrasRating"
//        title = barTitle

        Picasso.with(this)
                .load(BACKDROP_BASE_URL
                        + movieExtrasPoster)
                .into(ivPoster)

        Picasso.with(this)
                .load(BACKDROP_BASE_URL
                        + movieExtrasBackdrop)
                .into(ivBackdrop)

        tvOverview.text = movieExtrasOverview
        val releaseText = "${getString(R.string.release_text)} $movieExtrasReleaseDate"
        tvReleaseDate.text = releaseText
        val ratingText = "${getString(R.string.rating_text)} $movieExtrasRating"
        tvRating.text = ratingText
    }

    private fun initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transition = Slide()
            transition.excludeTarget(android.R.id.statusBarBackground, true)
            window.enterTransition = transition
            window.returnTransition = transition
        }
    }
}
