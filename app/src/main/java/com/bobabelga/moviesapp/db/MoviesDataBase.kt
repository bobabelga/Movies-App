package com.bobabelga.moviesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bobabelga.moviesapp.models.Result
import com.bobabelga.moviesapp.models.WatchedMovie

@Database(entities = [Result::class,WatchedMovie::class], version = 1)
abstract class MoviesDataBase : RoomDatabase() {
    abstract fun getDao(): MovieDao
}