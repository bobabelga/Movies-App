package com.bobabelga.moviesapp.repository

import com.bobabelga.moviesapp.db.MovieDao
import com.bobabelga.moviesapp.models.MovieResponse
import com.bobabelga.moviesapp.models.Result
import com.bobabelga.moviesapp.models.WatchedMovie
import com.bobabelga.moviesapp.network.ApiService
import com.bobabelga.moviesapp.utils.constant.API_KEY
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) {

    suspend fun getAllMoviesFromApi(): Response<MovieResponse> {
        return apiService.getAllMoviesFromApi(API_KEY, "en-US", 1)
    }
  suspend fun searchMoviesFromApi(searchString: String): Response<MovieResponse> {
        return apiService.searchMoviesFromApi(API_KEY, "en-US", searchString)
    }

    suspend fun getFavMoviesFromDb(): List<Result> {
        return movieDao.getAllMovies()
    }

    suspend fun insetMovie(result: Result) {
        movieDao.insertMovie(result)
    }

    suspend fun deleteMovie(result: Result) {
        movieDao.deleteMovie(result)
    }

    suspend fun getAllWatchedMoviesFromDb(): List<WatchedMovie> {
        return movieDao.getAllWatchedMovies()
    }

    suspend fun insetWatchedMovie(watchedMovie:WatchedMovie ) {
        movieDao.insertWatchedMovie(watchedMovie)
    }

    suspend fun deleteAllWatchedMovies() {
        movieDao.deleteAllWatchedMovies()
    }
}