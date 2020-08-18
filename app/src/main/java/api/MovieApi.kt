package api

import com.example.moviesnews.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieApi  {

    @GET("popular?api_key=7882262c37362758b7e07865fb261576")
    fun getPopularMovies():Call<MovieResponse>

    @GET("top_rated?api_key=7882262c37362758b7e07865fb261576")
    fun getTopRatedMovies():Call<MovieResponse>

    @GET("upcoming?api_key=7882262c37362758b7e07865fb261576")
    fun getUpComingMovies():Call<MovieResponse>
}