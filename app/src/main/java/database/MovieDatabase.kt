package database

import androidx.room.Database
import androidx.room.RoomDatabase
import models.Model

@Database (entities = [ Model::class ], version=1 )
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}