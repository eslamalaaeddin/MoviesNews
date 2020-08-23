package database

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import com.example.moviesnews.Model

@Database (entities = [ Model::class ], version=1 )
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}