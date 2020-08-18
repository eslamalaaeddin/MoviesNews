package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesnews.Movie
import com.example.moviesnews.MoviesRepository

class TopRatedMoviesViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()
    val topRatedMoviesLiveData : LiveData<List<Movie>> = moviesRepository.getTopRatedMovies()
}