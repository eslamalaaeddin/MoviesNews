package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import models.Movie
import com.example.moviesnews.MoviesRepository

class TopRatedMoviesViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()
    val topRatedMoviesLiveData : LiveData<List<Movie>> = liveData {
        val data = moviesRepository.getTopRatedMovies()
        emit(data)
    }
}