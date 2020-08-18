package com.example.moviesnews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import api.MovieApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/3/movie/"
private const val TAG = "MoviesRepository"
class MoviesRepository {
    private val movieApi:MovieApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

         movieApi = retrofit.create(MovieApi::class.java)
    }


    //this function is for fetching popular movies
    fun getPopularMovies () : LiveData<List<Movie>> {

        val responseLiveData = MutableLiveData<List<Movie>>()
        val popularMoviesRequest = movieApi.getPopularMovies()

        popularMoviesRequest.enqueue(object : Callback<MovieResponse>{
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val moviesResponseList : List<Movie>? = response.body()?.results
                responseLiveData.value = moviesResponseList
            }
        })
        return responseLiveData
    }

    //this function is for fetching top rated movies
    fun getTopRatedMovies () : LiveData<List<Movie>> {

        val responseLiveData = MutableLiveData<List<Movie>>()
        val popularMoviesRequest = movieApi.getTopRatedMovies()

        popularMoviesRequest.enqueue(object : Callback<MovieResponse>{
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val moviesResponseList : List<Movie>? = response.body()?.results
                responseLiveData.value = moviesResponseList
            }
        })
        return responseLiveData
    }

    //this function is for fetching upcoming movies
    fun getUpComingMovies () : LiveData<List<Movie>> {

        val responseLiveData = MutableLiveData<List<Movie>>()
        val popularMoviesRequest = movieApi.getUpComingMovies()

        popularMoviesRequest.enqueue(object : Callback<MovieResponse>{
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val moviesResponseList : List<Movie>? = response.body()?.results
                responseLiveData.value = moviesResponseList
            }
        })
        return responseLiveData
    }


}