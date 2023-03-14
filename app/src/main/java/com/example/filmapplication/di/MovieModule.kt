package com.example.filmapplication.di

import com.example.filmapplication.model.MovieModel
import com.example.filmapplication.repository.MovieRepository
import com.example.filmapplication.repository.MovieRepositoryImpl
import com.example.filmapplication.service.OMDbMovieAPI
import com.example.filmapplication.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val movieModule  = module {

    //creates a singleton
    single {
        val BASE_URL = "https://www.omdbapi.com/"
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OMDbMovieAPI::class.java)
    }

    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }

    viewModel{
        MovieViewModel(get())
    }

}