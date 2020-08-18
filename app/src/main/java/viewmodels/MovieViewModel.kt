package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesnews.DetailedMovie
import com.example.moviesnews.Movie
import com.example.moviesnews.MoviesRepository

class MovieViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()
    val detailedMovieLiveData : LiveData<DetailedMovie> = moviesRepository.getMovieDetails()
}