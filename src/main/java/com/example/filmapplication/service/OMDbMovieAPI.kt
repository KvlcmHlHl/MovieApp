package com.example.filmapplication.service

import com.example.filmapplication.model.MovieModel
import com.example.filmapplication.model.SearchModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "79d79a32"

interface OMDbMovieAPI {

    @GET(".")
    suspend fun getData(@Query("s") searchString :String,
                        @Query("apikey") apiKey :String = API_KEY ): Response<SearchModel>

    @GET("movieDetail")
    suspend fun getMovieDetail(
        @Query("i") imdbId : String,
        @Query("apikey") apiKey: String = API_KEY
    ) : Response<MovieModel>
}