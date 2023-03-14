package com.example.filmapplication.repository

import com.example.filmapplication.model.MovieModel
import com.example.filmapplication.model.SearchModel
import com.example.filmapplication.util.Resource

interface MovieRepository {
    suspend fun getMovieInfo( title:String="abc") : Resource<SearchModel>
    suspend fun getMovieDetail( title:String="abc") : Resource<MovieModel>
}