package com.bobabelga.moviesapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bobabelga.moviesapp.R
import com.bobabelga.moviesapp.adapters.FavoriteAdapter
import com.bobabelga.moviesapp.adapters.MovieAdapter
import com.bobabelga.moviesapp.models.Result
import com.bobabelga.moviesapp.models.WatchedMovie
import com.bobabelga.moviesapp.ui.MovieDetailsActivity
import com.bobabelga.moviesapp.ui.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()
    private val favoriteAdapter = FavoriteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite_movies, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.favRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = favoriteAdapter

        movieViewModel.favMoviesFromDbMutableLiveData.observe(viewLifecycleOwner, Observer {
            favoriteAdapter.setMoviesList(it as ArrayList<Result>)
        })

        favoriteAdapter.setOnClickShowDetailsListener {
            val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
            intent.putExtra("MOVIE", it)
            startActivity(intent)
        }
        favoriteAdapter.setOnClickRemoveListener {
            movieViewModel.removeMovieFromFavorite(it)
            movieViewModel.getAllMoviesFromDB()
        }

        favoriteAdapter.setOnClickAddToWatchedListener {
            movieViewModel.insetWatchedMovie(WatchedMovie(it.id))
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        movieViewModel.getAllMoviesFromDB()
    }
}