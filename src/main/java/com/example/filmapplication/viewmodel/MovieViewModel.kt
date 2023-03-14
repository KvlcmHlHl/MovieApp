package com.example.filmapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapplication.model.MovieModel
import com.example.filmapplication.model.SearchModel
import com.example.filmapplication.repository.MovieRepository
import com.example.filmapplication.service.OMDbMovieAPI
import com.example.filmapplication.util.Resource
import kotlinx.coroutines.*

class MovieViewModel(private val modelDownLoadRep: MovieRepository) : ViewModel()
{
    val movieError = MutableLiveData<Resource<Boolean>>()
    val movieLoading = MutableLiveData<Resource<Boolean>>()
    val movieList = MutableLiveData<Resource<SearchModel>>()
    val movie = MutableLiveData<Resource<MovieModel>>()

    private var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
        println(throwable.localizedMessage)
        movieError.value =Resource.error(throwable.localizedMessage ?: "Error", true)
    }

    fun getDataFromAPI(title: String="")
    {
        movieLoading.value= Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = modelDownLoadRep.getMovieInfo(title)
            println("MovieModel- getDataFromAPI1 {$title}")

            withContext(Dispatchers.Main)
            {
                println("MovieModel- getDataFromAPI2 {$title}")

                resource.data?.let {
                    println("MovieModel- getDataFromAPI3 {$title}")

                    movieList.value= resource
                    movieLoading.value =Resource.loading(false)
                    movieError.value =Resource.error("", false)
                    println("MovieModel- getDataFromAPI4 {$title}")
                }
            }

        }
    }

    fun getMovieDetailFromAPI(imdbID: String="")
    {
        movieLoading.value= Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val movieData = modelDownLoadRep.getMovieDetail(imdbID)

            withContext(Dispatchers.Main)
            {
                movieData.data?.let {
                    movie.value= movieData
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()

    }
}