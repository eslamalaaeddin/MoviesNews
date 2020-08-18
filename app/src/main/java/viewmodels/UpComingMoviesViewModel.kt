package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesnews.Movie
import com.example.moviesnews.MoviesRepository

class UpComingMoviesViewModel:ViewModel() {
    private val moviesRepository = MoviesRepository()
    val upComingMoviesLiveData : LiveData<List<Movie>> = moviesRepository.getUpComingMovies()
}