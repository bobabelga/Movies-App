package com.bobabelga.moviesapp.network

import com.bobabelga.moviesapp.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //movie/popular?api_key=bfe3dbe00241214d46bc44750ab7592e&language=en-US&page=1
    @GET("movie/popular")
    suspend fun getAllMoviesFromApi (
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("page") page:Int
    ) : Response<MovieResponse>

    //movie/popular?api_key=bfe3dbe00241214d46bc44750ab7592e&language=en-US&page=1
    @GET("search/movie")
    suspend fun searchMoviesFromApi (
        @Query("api_key") api_key:String,
        @Query("language") language:String,
        @Query("query") query:String
    ) : Response<MovieResponse>
}