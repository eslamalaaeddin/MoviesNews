package database

import androidx.room.Database
import androidx.room.RoomDatabase
import models.FavoriteMovieModel

@Database (entities = [ FavoriteMovieModel::class ], version=1, exportSchema = false )
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}