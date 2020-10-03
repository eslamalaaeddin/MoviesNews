package di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import database.MovieDao
import database.MovieDatabase
import javax.inject.Singleton

private const val DATABASE_NAME = "movie-database"
@Module
@InstallIn(ApplicationComponent::class)
class RoomModule {

    companion object{
        @Provides
        @Singleton
        fun getDao (@ApplicationContext context: Context) : MovieDao{
            return Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                DATABASE_NAME
            ).build().getMovieDao()
        }
    }
}