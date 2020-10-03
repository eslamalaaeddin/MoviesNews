package viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import models.Movie
import com.example.moviesnews.MoviesRepository

class TopRatedMoviesViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val topRatedMoviesLiveData : LiveData<List<Movie>> = liveData {
        val data = moviesRepository.getTopRatedMovies()
        emit(data)
    }
}