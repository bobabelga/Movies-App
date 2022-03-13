package com.bobabelga.moviesapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bobabelga.moviesapp.R
import com.bobabelga.moviesapp.adapters.MovieAdapter
import com.bobabelga.moviesapp.models.Result
import com.bobabelga.moviesapp.models.WatchedMovie
import com.bobabelga.moviesapp.ui.MovieDetailsActivity
import com.bobabelga.moviesapp.ui.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchMoviesFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModels()
    private val movieAdapter = MovieAdapter()
    private var searchString = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_movies, container, false)
        val searchEditText : TextView = view.findViewById(R.id.searchEditText)
        val searchBtn : Button = view.findViewById(R.id.searchBtn)
        val clearWatchedListBtn : Button = view.findViewById(R.id.clearWatchedListBtn)
        val recyclerView: RecyclerView = view.findViewById(R.id.searchRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = movieAdapter

        searchBtn.setOnClickListener {
            searchString = searchEditText.text.toString()
            movieViewModel.getSearchMovies(searchString)
        }

        clearWatchedListBtn.setOnClickListener {
            searchString = searchEditText.text.toString()
            movieViewModel.deleteAllWatchedMovies()
            movieViewModel.getSearchMovies(searchString)
        }

        movieViewModel.searchMovieMutableLiveData.observe(viewLifecycleOwner, Observer {
            movieAdapter.setMoviesList(it as ArrayList<Result>)
        })

        movieAdapter.setOnClickShowDetailsListener {
            val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
            intent.putExtra("MOVIE", it)
            startActivity(intent)
        }

        movieAdapter.setOnClickInsertListener {
            Toast.makeText(requireContext(), "Added To Favorite "+it.title, Toast.LENGTH_SHORT).show()
            movieViewModel.insertMovieToFavorite(it)

        }
        movieAdapter.setOnClickAddToWatchedListener {
            movieViewModel.insetWatchedMovie(WatchedMovie(it.id))
            movieViewModel.getSearchMovies(searchString)
        }

        return view
    }
    override fun onResume() {
        super.onResume()
        movieViewModel.getSearchMovies(searchString)
    }
}