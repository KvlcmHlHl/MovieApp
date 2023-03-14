package com.example.filmapplication.repository

import com.example.filmapplication.model.MovieModel
import com.example.filmapplication.model.SearchModel
import com.example.filmapplication.service.OMDbMovieAPI
import com.example.filmapplication.util.Resource
import com.example.filmapplication.util.Status

class MovieRepositoryImpl(private val api: OMDbMovieAPI) :MovieRepository{
    override suspend fun getMovieInfo(title : String): Resource<SearchModel> {
        return try {
            val response = api.getData(title)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)            } else {
                return Resource.error("Error Text:Internet bağlantınızı kontrol edin", null)
            }

        } catch(e : java.lang.Exception)
        {
            Resource.error("Error Text: Veri alinamadi", null)
        }
    }

    override suspend fun getMovieDetail(title: String): Resource<MovieModel> {
        return try {
            val response = api.getMovieDetail(title)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)            } else {
                return Resource.error("Error Text:Internet bağlantınızı kontrol edin", null)
            }

        } catch(e : java.lang.Exception)
        {
            Resource.error("Error Text: Veri alinamadi", null)
        }    }

}