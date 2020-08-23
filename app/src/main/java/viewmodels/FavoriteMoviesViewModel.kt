package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import models.DetailedMovie
import com.example.moviesnews.MoviesRepository

class FavoriteMoviesViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()
    private val favoriteMovies = mutableListOf<DetailedMovie>()

    val favoriteMoviesLiveData : LiveData<MutableList<DetailedMovie>> = liveData {
        val favoriteMoviesFromDb = moviesRepository.getMovies()
        for (movie in favoriteMoviesFromDb) {
            val favMovie = moviesRepository.getFavoriteMovies(movie.movieId)
            favoriteMovies.add(favMovie)
        }

        emit(favoriteMovies)
    }

}