package com.example.moviesnews

import database.MovieDao
import models.DetailedMovie
import models.FavoriteMovieModel
import models.Movie
import models.Result
import network.MovieApi
import javax.inject.Inject

class MoviesRepository @Inject constructor() {

   @Inject lateinit var movieApi: MovieApi
   @Inject lateinit var movieDao: MovieDao

   //Networking stuff

    suspend fun getPopularMovies () :List<Movie> = movieApi.getPopularMovies().results

    suspend fun getTopRatedMovies () : List<Movie> = movieApi.getTopRatedMovies().results

    suspend fun getUpComingMovies () : List<Movie> = movieApi.getUpComingMovies().results

    suspend fun getMovieDetails() : DetailedMovie =  movieApi.getMovieDetails(Utils.itemId)

    suspend fun getMovieRecommendations () : List<Movie> = movieApi.getMovieRecommendations(Utils.itemId).results

    suspend fun getMovieTrailer () : List<Result>  = movieApi.getMovieTrailer(Utils.itemId).results

    suspend fun getFavoriteMovies(movieId:Int) : DetailedMovie =  movieApi.getFav(movieId)

    // Database stuff

    suspend fun getMovies() : List<FavoriteMovieModel> = movieDao.getMovies()

    suspend fun getMovie(movieId:Int) : FavoriteMovieModel = movieDao.getMovie(movieId)

    suspend fun insertMovie(model: FavoriteMovieModel) = movieDao.insertMovie(model)

    suspend fun deleteMovie(model: FavoriteMovieModel) = movieDao.deleteMovie(model)

}