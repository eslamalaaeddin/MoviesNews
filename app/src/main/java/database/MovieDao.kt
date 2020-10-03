package database

import androidx.room.*
import dagger.Provides
import models.Model


@Dao
interface MovieDao {

    @Query ("SELECT * FROM model")
    suspend fun getMovies() : List<Model>

    @Query ("SELECT * FROM model WHERE movieId=(:itemId)")
    suspend fun getMovie (itemId:Int): Model

    @Insert
    suspend fun insertMovie(movieModel: Model)

    @Delete
    suspend fun deleteMovie(movieModel: Model)


}