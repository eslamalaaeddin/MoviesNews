package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesnews.*

class MovieViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()
    val detailedMovieLiveData : LiveData<DetailedMovie> = moviesRepository.getMovieDetails()

    val recommendedMoviesLiveData : LiveData<List<Movie>> = moviesRepository.getMovieRecommendations()
    val movieTrailersLiveData : LiveData<List<Result>> = moviesRepository.getMovieTrailer()

}