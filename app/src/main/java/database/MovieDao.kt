package database

import androidx.room.*
import models.FavoriteMovieModel


@Dao
interface MovieDao {

    @Query ("SELECT * FROM favoritemoviemodel")
    suspend fun getMovies() : List<FavoriteMovieModel>

    @Query ("SELECT * FROM favoritemoviemodel WHERE movieId=(:itemId)")
    suspend fun getMovie (itemId:Int): FavoriteMovieModel

    @Insert
    suspend fun insertMovie(movieModel: FavoriteMovieModel)

    @Delete
    suspend fun deleteMovie(movieModel: FavoriteMovieModel)


}