package com.moolajoo.popularmovies.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.moolajoo.popularmovies.R
import com.moolajoo.popularmovies.model.MovieResponse
import com.moolajoo.popularmovies.util.POSTER_BASE_URL
import com.squareup.picasso.Picasso

/**
 * Created by joaopaulotargino on 2018-03-02.
 */

class GridMoviesAdapter(context: Context, movieList: List<MovieResponse.MovieData>, val itemClick: (MovieResponse.MovieData) -> Unit) : RecyclerView.Adapter<GridMoviesAdapter.Holder>() {
    val context = context
    val mMovieList = movieList

    override fun getItemCount(): Int {
        return mMovieList.count()
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bind(mMovieList[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val gridView = LayoutInflater.from(context)
                .inflate(R.layout.poster_item, parent, false)
        return Holder(gridView, itemClick)

    }


    inner class Holder(itemView: View?, val itemClick: (MovieResponse.MovieData) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val moviePoster = itemView?.findViewById<ImageView>(R.id.ivPoster)
        val movieTitle = itemView?.findViewById<TextView>(R.id.tvTitle)
        val movieRating = itemView?.findViewById<TextView>(R.id.tvRating)

        fun bind(movie: MovieResponse.MovieData, context: Context) {
            val movie = mMovieList[position]
            movieTitle?.text = movie.movieTitle
            movieRating?.text = movie.vote_average.toString()
            Picasso.with(context)
                    .load(POSTER_BASE_URL + movie.poster_path)
                    .into(moviePoster)

            itemView.contentDescription = movie.movieTitle

            itemView.setOnClickListener { itemClick(movie) }
        }

    }


}