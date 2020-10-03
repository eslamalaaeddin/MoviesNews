package viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import models.DetailedMovie
import com.example.moviesnews.MoviesRepository

class FavoriteMoviesViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel()  {

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