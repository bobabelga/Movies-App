package com.bobabelga.moviesapp.di

import android.content.Context
import androidx.room.Room
import com.bobabelga.moviesapp.db.MoviesDataBase
import com.bobabelga.moviesapp.network.ApiService
import com.bobabelga.moviesapp.utils.constant.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {


    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            MoviesDataBase::class.java,
            "movies_database"
        ).build()

    @Singleton
    @Provides
    fun provideMovieDao(db: MoviesDataBase) = db.getDao()


}