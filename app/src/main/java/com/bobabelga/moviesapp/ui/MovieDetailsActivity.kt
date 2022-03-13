package com.bobabelga.moviesapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.bobabelga.moviesapp.R
import com.bobabelga.moviesapp.models.Result
import com.bumptech.glide.Glide

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movie = intent.getSerializableExtra("MOVIE") as? Result

        val titleMovieDetails: TextView = findViewById(R.id.titleMovieDetails)
        titleMovieDetails.text = movie!!.title
        val textMovieDetails: TextView = findViewById(R.id.textMovieDetails)
        textMovieDetails.text = movie.overview
        val releaseDate: TextView = findViewById(R.id.releaseDate)
        releaseDate.text = movie.release_date

        val posterImageView: ImageView = findViewById(R.id.posterImageView)

        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
            .into(posterImageView)

        val animation: LottieAnimationView = findViewById(R.id.animationView)
        val rating = movie.vote_average/2
        val ratingFormatedString: String = "%.1f".format(rating)
        val ratingFormated = ratingFormatedString.toDouble()
        animation.addAnimatorUpdateListener { valueAnimator ->

            var valueAnimFormated: String? = "%.2f".format(valueAnimator.animatedValue)
            val progress = valueAnimFormated?.toDouble()?.times(10)
            Log.d("Rating", "Value Animator : $progress Rating is :$ratingFormated")
            if (progress!! in ratingFormated - 0.1 .. ratingFormated + 0.1)
                animation.pauseAnimation()
        }


    }
}