package database

import androidx.lifecycle.LiveData
import androidx.room.*
import models.FavoriteMovieModel


@Dao
interface MovieDao {

//    @Query ("SELECT * FROM favoritemoviemodel")
//    suspend fun getMovies() : List<FavoriteMovieModel>

    //when i use coroutines i have to click the favorite heart button to get the new favorite movies from db

    @Query ("SELECT * FROM favoritemoviemodel")
    fun getMovies() : LiveData<List<FavoriteMovieModel>>

    @Query ("SELECT * FROM favoritemoviemodel WHERE movieId=(:itemId)")
    suspend fun getMovie (itemId:Int): FavoriteMovieModel

    @Insert
    suspend fun insertMovie(movieModel: FavoriteMovieModel)

    @Delete
    suspend fun deleteMovie(movieModel: FavoriteMovieModel)


}