package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesnews.Movie
import com.example.moviesnews.MoviesRepository

class PopularMoviesViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()
    val popularMoviesLiveData : LiveData<List<Movie>> = moviesRepository.getPopularMovies()
}