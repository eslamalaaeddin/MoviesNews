package api

import com.example.moviesnews.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
private const val apiKey ="7882262c37362758b7e07865fb261576"
interface MovieApi  {



    @GET("popular?api_key=$apiKey")
    fun getPopularMovies():Call<MovieResponse>

    @GET("top_rated?api_key=$apiKey")
    fun getTopRatedMovies():Call<MovieResponse>

    @GET("upcoming?api_key=$apiKey")
    fun getUpComingMovies():Call<MovieResponse>

    @GET("{movie_id}?api_key=$apiKey")
    fun getMovieDetails( @Path ("movie_id") id:Int):Call<DetailedMovie>

    @GET("{movie_id}/recommendations?api_key=$apiKey")
    fun getMovieRecommendations( @Path ("movie_id") id:Int):Call<MovieResponse>

    @GET("{movie_id}/videos?api_key=$apiKey")
    fun getMovieTrailer(@Path("movie_id")id:Int):Call<MovieTrailerModel>



}