package network

import models.DetailedMovie
import models.MovieResponse
import models.MovieTrailerModel
import retrofit2.http.GET
import retrofit2.http.Path
private const val apiKey ="7882262c37362758b7e07865fb261576"
interface MovieApi  {

    @GET("popular?api_key=$apiKey")
    suspend fun getPopularMovies(): MovieResponse

    @GET("top_rated?api_key=$apiKey")
    suspend fun getTopRatedMovies(): MovieResponse

    @GET("upcoming?api_key=$apiKey")
    suspend fun getUpComingMovies(): MovieResponse

    @GET("{movie_id}?api_key=$apiKey")
    suspend fun getMovieDetails( @Path ("movie_id") id:Long): DetailedMovie

    @GET("{movie_id}/recommendations?api_key=$apiKey")
    suspend fun getMovieRecommendations( @Path ("movie_id") id:Long): MovieResponse

    @GET("{movie_id}/videos?api_key=$apiKey")
    suspend fun getMovieTrailer(@Path("movie_id")id:Long): MovieTrailerModel

    @GET("{movie_id}?api_key=$apiKey")
    suspend fun getFav( @Path ("movie_id") id:Long): DetailedMovie

}