package com.bobabelga.moviesapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobabelga.moviesapp.models.Result
import com.bobabelga.moviesapp.models.WatchedMovie
import com.bobabelga.moviesapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val moviesFromApiMutableLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    val searchMovieMutableLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    val favMoviesFromDbMutableLiveData: MutableLiveData<List<Result>> = MutableLiveData()
    var watchedMoviesList = ArrayList<Int>()

    fun getAllMovies() {
        viewModelScope.launch {
            try {
                moviesFromApiMutableLiveData.value =
                    repository.getAllMoviesFromApi().body()!!.results
            } catch (ex: Exception) {
                Log.d("Boba", ex.message.toString())
            }
        }
    }

    fun getSearchMovies(searchString: String) {
        viewModelScope.launch {
            try {
                updateWatchedList()
                if (repository.searchMoviesFromApi(searchString).isSuccessful) {
                    if (watchedMoviesList.isNotEmpty()) {
                        searchMovieMutableLiveData.value =
                            repository.searchMoviesFromApi(searchString)
                                .body()!!.results.filterNot {
                                it.id in watchedMoviesList
                            }
                    } else {
                        searchMovieMutableLiveData.value =
                            repository.searchMoviesFromApi(searchString).body()!!.results
                    }
                }
            } catch (ex: Exception) {
                Log.d("Boba", ex.message.toString())
            }
        }
    }

    fun getAllMoviesFromDB() {
        viewModelScope.launch {
            try {
                favMoviesFromDbMutableLiveData.value = repository.getFavMoviesFromDb()
            } catch (ex: Exception) {
                Log.d("Boba", ex.message.toString())
            }
        }
    }


    fun insertMovieToFavorite(movie: Result) {
        viewModelScope.launch {
            repository.insetMovie(movie)
        }
    }

    fun removeMovieFromFavorite(movie: Result) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }

    fun deleteAllWatchedMovies() {
        viewModelScope.launch {
            watchedMoviesList.clear()
            repository.deleteAllWatchedMovies()
        }
    }

    fun insetWatchedMovie(watchedMovie: WatchedMovie) {
        viewModelScope.launch {
            repository.insetWatchedMovie(watchedMovie)
            updateWatchedList()
            for (i in watchedMoviesList) {
                Log.d("Boba", "Id : $i")
            }
        }
    }

    private fun updateWatchedList() {
        viewModelScope.launch {
            watchedMoviesList.clear()
            for (i in repository.getAllWatchedMoviesFromDb()) {
                watchedMoviesList.add(i.id)
            }
        }
    }
}