package com.bobabelga.moviesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bobabelga.moviesapp.R
import com.bobabelga.moviesapp.models.Result
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MovieViewHolder>() {
    private var moviesList: ArrayList<Result> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.titelTextView.text = moviesList[position].title
        holder.releaseDate.text = moviesList[position].release_date
        holder.ratingTextView.text = moviesList[position].vote_average.toString()


        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w500/" + moviesList[position].poster_path)
            .into(holder.posterImageView)

        holder.showDetailsBtn.setOnClickListener {
            OnClickShowDetailsListener?.let { it(moviesList[position]) }
        }
        holder.removeFavBtn.setOnClickListener {
            OnClickRemoveListener?.let { it(moviesList[position]) }
        }
        holder.removeFavoriteBtn.setOnClickListener {
            OnClickAddToWatchedListener?.let { it(moviesList[position]) }
        }

    }

    private var OnClickShowDetailsListener: ((Result) -> Unit)? = null
    private var OnClickRemoveListener: ((Result) -> Unit)? = null
    private var OnClickAddToWatchedListener: ((Result) -> Unit)? = null


    fun setOnClickShowDetailsListener(listener: (Result) -> Unit) {
        OnClickShowDetailsListener = listener
    }

    fun setOnClickRemoveListener(listener: (Result) -> Unit) {
        OnClickRemoveListener = listener
    }

    fun setOnClickAddToWatchedListener(listener: (Result) -> Unit) {
        OnClickAddToWatchedListener = listener
    }


    fun setMoviesList(list: ArrayList<Result>) {
        moviesList = list
        notifyDataSetChanged()
    }


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titelTextView: TextView
        val releaseDate: TextView
        val ratingTextView: TextView
        val showDetailsBtn: MaterialButton
        val removeFavBtn: Button
        val removeFavoriteBtn: Button
        val posterImageView: ImageView

        init {
            titelTextView = itemView.findViewById(R.id.titleMovieDetails)
            releaseDate = itemView.findViewById(R.id.releaseDate)
            ratingTextView = itemView.findViewById(R.id.ratingTextView)
            showDetailsBtn = itemView.findViewById(R.id.showMovieDetailsBtn)
            removeFavoriteBtn = itemView.findViewById(R.id.addToWatched)
            removeFavBtn = itemView.findViewById(R.id.removeFavBtn)
            posterImageView = itemView.findViewById(R.id.posterImageView)
        }
    }
}