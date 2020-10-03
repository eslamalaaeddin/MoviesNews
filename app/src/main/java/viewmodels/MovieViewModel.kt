package viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.moviesnews.*
import models.DetailedMovie
import models.Movie
import models.Result

class MovieViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    val detailedMovieLiveData : LiveData<DetailedMovie> = liveData {
        val data = moviesRepository.getMovieDetails()
        emit(data)
    }

    val recommendedMoviesLiveData : LiveData<List<Movie>> = liveData {
        val data = moviesRepository.getMovieRecommendations()
        emit(data)
    }
    val movieTrailersLiveData : LiveData<List<Result>> = liveData {
        val data =  moviesRepository.getMovieTrailer()
        emit(data)
    }

}