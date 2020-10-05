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
private const val ID = "id"
class MovieViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private val movieId = savedStateHandle.get<Long>(ID)!!

    val detailedMovieLiveData : LiveData<DetailedMovie> = liveData {
        val data = moviesRepository.getMovieDetails(movieId)
        emit(data)
    }

    val recommendedMoviesLiveData : LiveData<List<Movie>> = liveData {
        val data = moviesRepository.getMovieRecommendations(movieId)
        emit(data)
    }
    val movieTrailersLiveData : LiveData<List<Result>> = liveData {
        val data =  moviesRepository.getMovieTrailer(movieId)
        emit(data)
    }

}