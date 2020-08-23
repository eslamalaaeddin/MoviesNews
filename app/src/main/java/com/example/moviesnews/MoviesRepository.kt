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
    suspend fun getPopularMovies () :List<Movie> = movieApi.getPopularMovies().results


    //this function is for fetching top rated movies
    suspend fun getTopRatedMovies () : List<Movie> = movieApi.getTopRatedMovies().results

    //this function is for fetching upcoming movies
    suspend fun getUpComingMovies () : List<Movie> = movieApi.getUpComingMovies().results

    //to get a movies details
    suspend fun getMovieDetails() : DetailedMovie =  movieApi.getMovieDetails(Utils.itemId)


    //this function is for fetching  movie recommendations
    suspend fun getMovieRecommendations () : List<Movie> = movieApi.getMovieRecommendations(Utils.itemId).results

    //movieTrailer
    suspend fun getMovieTrailer () : List<Result>  = movieApi.getMovieTrailer(Utils.itemId).results

    //to get a movies details
    suspend fun getFavoriteMovies(movieId:Int) : DetailedMovie  =  movieApi.getFav(movieId)


    /*
        Database working
     */


    private val movieDao = getDbInstance().getMovieDao()

    suspend fun getMovies() : List<Model> = movieDao.getMovies()
    var movie:Model? = null
    suspend fun getMovie(movieId:Int) : Model = movieDao.getMovie(movieId)

    suspend fun insertMovie(model: Model) = movieDao.insertMovie(model)

    suspend fun deleteMovie(model: Model) = movieDao.deleteMovie(model)

    suspend fun updateMovie(model: Model) = movieDao.updateMovie(model)

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