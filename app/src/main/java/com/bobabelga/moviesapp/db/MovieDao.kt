package com.bobabelga.moviesapp.db

import androidx.room.*
import com.bobabelga.moviesapp.models.Result
import com.bobabelga.moviesapp.models.WatchedMovie


@Dao
interface MovieDao {

    // Favorite Movies
    @Query("SELECT * FROM movie_table")
    suspend fun getAllMovies(): List<Result>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(result : Result)

    @Delete
    suspend fun deleteMovie(result : Result)

    // Watched Movies

    @Query("SELECT * FROM watched_movies_table")
    suspend fun getAllWatchedMovies(): List<WatchedMovie>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchedMovie(watchedMovie : WatchedMovie)

    @Query("DELETE FROM watched_movies_table")
    suspend fun deleteAllWatchedMovies()
}