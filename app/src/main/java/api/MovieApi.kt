package api

import com.example.moviesnews.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi  {

    @GET("popular?api_key=7882262c37362758b7e07865fb261576")
    fun getPopularMovies():Call<MovieResponse>

    @GET("top_rated?api_key=7882262c37362758b7e07865fb261576")
    fun getTopRatedMovies():Call<MovieResponse>

    @GET("upcoming?api_key=7882262c37362758b7e07865fb261576")
    fun getUpComingMovies():Call<MovieResponse>

    @GET("{movie_id}?api_key=7882262c37362758b7e07865fb261576")
    fun getMovieDetails( @Path ("movie_id") id:Int):Call<DetailedMovie>

    @GET("{movie_id}/recommendations?api_key=7882262c37362758b7e07865fb261576")
    fun getMovieRecommendations( @Path ("movie_id") id:Int):Call<MovieResponse>

    @GET("{movie_id}/videos?api_key=7882262c37362758b7e07865fb261576")
    fun getMovieTrailer(@Path("movie_id")id:Int):Call<MovieTrailerModel>

}