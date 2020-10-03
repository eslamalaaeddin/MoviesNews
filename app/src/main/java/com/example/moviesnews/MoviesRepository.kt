package com.example.moviesnews

import android.content.Context
import database.MovieDao
import network.MovieApi
import di.RetrofitModule
import di.RoomModule
import models.DetailedMovie
import models.Model
import models.Movie
import models.Result
import java.security.AccessController.getContext
import javax.inject.Inject

private val movieDao = RoomModule.getRoomDbInstance(MoviesRepository.getContext()).getMovieDao()

private val movieApi = RetrofitModule.getMovieApi()

class MoviesRepository @Inject constructor() {

    /*
      Networking stuff
   */

    suspend fun getPopularMovies () :List<Movie> = movieApi.getPopularMovies().results

    suspend fun getTopRatedMovies () : List<Movie> = movieApi.getTopRatedMovies().results

    suspend fun getUpComingMovies () : List<Movie> = movieApi.getUpComingMovies().results

    suspend fun getMovieDetails() : DetailedMovie =  movieApi.getMovieDetails(Utils.itemId)

    suspend fun getMovieRecommendations () : List<Movie> = movieApi.getMovieRecommendations(Utils.itemId).results

    suspend fun getMovieTrailer () : List<Result>  = movieApi.getMovieTrailer(Utils.itemId).results

    suspend fun getFavoriteMovies(movieId:Int) : DetailedMovie =  movieApi.getFav(movieId)


    /*
        Database stuff
     */

    suspend fun getMovies() : List<Model> = movieDao.getMovies()

    suspend fun getMovie(movieId:Int) : Model = movieDao.getMovie(movieId)

    suspend fun insertMovie(model: Model) = movieDao.insertMovie(model)

    suspend fun deleteMovie(model: Model) = movieDao.deleteMovie(model)

    suspend fun updateMovie(model: Model) = movieDao.updateMovie(model)

    //to het a context instance
    companion object{
        private  var context : Context? = null

        fun provideContext(contextFromBaseApplication: Context) {
            if (context == null) {
                   context = contextFromBaseApplication
            }
        }

        fun getContext () : Context {
            return context ?: throw IllegalStateException("Context required")
        }

    }





}