package di

import network.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://api.themoviedb.org/3/movie/"

@Module
@InstallIn(ApplicationComponent::class)
class RetrofitModule {

    companion object{
        @Provides
        @Singleton
        fun getMovieApi () : MovieApi{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MovieApi::class.java)
        }

    }

}