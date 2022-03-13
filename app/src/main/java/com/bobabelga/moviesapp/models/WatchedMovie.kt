package com.bobabelga.moviesapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "watched_movies_table")
data class WatchedMovie(
    @PrimaryKey
    val id: Int
)