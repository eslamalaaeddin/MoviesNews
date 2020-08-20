package com.example.moviesnews

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import api.MovieApi
import database.MovieDatabase
import fragments.FavoriteMoviesFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

const val BASE_URL = "https://api.themoviedb.org/3/movie/"
private const val TAG = "MoviesRepository"
private const val DATABASE_NAME = "movie-database"
class MoviesRepository {
    private val movieApi:MovieApi
    private lateinit var detailedMovie: DetailedMovie
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

    //to get a movies details
    fun getMovieDetails() : LiveData<DetailedMovie> {
        val responseLiveData = MutableLiveData<DetailedMovie>()
        val moviesRequest = movieApi.getMovieDetails(Utils.itemId)
        moviesRequest.enqueue(object : Callback<DetailedMovie> {
            override fun onFailure(call: Call<DetailedMovie>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(call: Call<DetailedMovie>, response: Response<DetailedMovie>) {
                 detailedMovie = response.body()!!
                responseLiveData.value = detailedMovie
                Log.i(TAG, "onResponse: $detailedMovie")
            }
        })

        return responseLiveData
    }

    //this function is for fetching  movie recommendations
    fun getMovieRecommendations () : LiveData<List<Movie>> {

        val responseLiveData = MutableLiveData<List<Movie>>()
        val recommendedMoviesRequest = movieApi.getMovieRecommendations(Utils.itemId)

        recommendedMoviesRequest.enqueue(object : Callback<MovieResponse>{
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                Log.i(TAG, "onResponse: ${response.body()?.results}")
                val moviesResponseList : List<Movie>? = response.body()?.results
                responseLiveData.value = moviesResponseList
            }
        })
        return responseLiveData
    }

    //movieTrailer
    fun getMovieTrailer () : MutableLiveData<List<Result>> {
        val trailerMovieRequest = movieApi.getMovieTrailer(Utils.itemId)
        val responseLiveData = MutableLiveData<List<Result>>()

        var key = ""
        trailerMovieRequest.enqueue(object : Callback<MovieTrailerModel> {
            override fun onFailure(call: Call<MovieTrailerModel>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(
                call: Call<MovieTrailerModel>,
                response: Response<MovieTrailerModel>
            ) {
                responseLiveData.value = response.body()?.results
                Log.i(TAG, "HAHA onResponse: ${response.body()?.results?.size}")
            }
        })

        return responseLiveData
    }

    //to get a movies details
    fun getFavoriteMovies(movieId:Int) : LiveData<DetailedMovie> {

        val responseLiveData = MutableLiveData<DetailedMovie>()
        val moviesRequest = movieApi.getMovieDetails(movieId)
        moviesRequest.enqueue(object : Callback<DetailedMovie> {
            override fun onFailure(call: Call<DetailedMovie>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message.toString()}")
            }

            override fun onResponse(call: Call<DetailedMovie>, response: Response<DetailedMovie>) {
                detailedMovie = response.body()!!
                responseLiveData.value = detailedMovie
                Log.i(TAG, "ALAA onResponse: $detailedMovie")
            }
        })

        return responseLiveData
    }

    /*
        Database working
     */

    private val executor = Executors.newSingleThreadExecutor()

    private val movieDao = getDbInstance().getMovieDao()

    fun getMovies() : LiveData<List<Model>> = movieDao.getMovies()
    var movie:Model? = null
    fun getMovie(movieId:Int) : LiveData<Model> = movieDao.getMovie(movieId)

    fun insertMovie(model: Model) {
        executor.execute {
            movieDao.insertMovie(model)
        }
    }

    fun deleteMovie(model: Model) {
        executor.execute {
            movieDao.deleteMovie(model)
        }
    }

    fun updateMovie(model: Model) {
        executor.execute {
            movieDao.updateMovie(model)
        }
    }

    //to het a database instance
    companion object{
        private  var databaseInstance : MovieDatabase? = null

        fun initialize(context: Context) {
            if (databaseInstance == null) {
                    databaseInstance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        DATABASE_NAME
                    ).build()
            }
        }

        fun getDbInstance () : MovieDatabase {
            return databaseInstance ?: throw IllegalStateException("Movie database must be initialized")
        }

    }





}