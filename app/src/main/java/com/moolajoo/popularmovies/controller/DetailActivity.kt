package com.moolajoo.popularmovies.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.moolajoo.popularmovies.R
import com.moolajoo.popularmovies.util.BACKDROP_BASE_URL
import com.moolajoo.popularmovies.util.EXTRA_MOVIE_POSTER
import com.moolajoo.popularmovies.util.EXTRA_MOVIE_TITLE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movieExtrasPoster = intent.getStringExtra(EXTRA_MOVIE_POSTER)
        val movieExtrasTitle = intent.getStringExtra(EXTRA_MOVIE_TITLE)

        setTitle(movieExtrasTitle)

        Picasso.with(this)
                .load(BACKDROP_BASE_URL
                        + movieExtrasPoster)
                .into(ivBackdrop)
    }
}
