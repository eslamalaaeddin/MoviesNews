package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import models.Movie
import com.example.moviesnews.MoviesRepository

class UpComingMoviesViewModel:ViewModel() {
    private val moviesRepository = MoviesRepository()
    val upComingMoviesLiveData : LiveData<List<Movie>> = liveData {
        val data = moviesRepository.getUpComingMovies()
        emit(data)
    }
}