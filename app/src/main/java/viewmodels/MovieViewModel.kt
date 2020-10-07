package viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.moviesnews.*
import models.DetailedMovie
import models.FavoriteMovieModel
import models.Movie
import models.Result
private const val ID = "id"
class MovieViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private val movieId = savedStateHandle.get<Long>(ID)!!

    //to get a movie details
    val detailedMovieLiveData : LiveData<DetailedMovie> = liveData {
        val data = moviesRepository.getMovieDetails(movieId)
        emit(data)
    }

    //to get a movie recommendations
    val recommendedMoviesLiveData : LiveData<List<Movie>> = liveData {
        val data = moviesRepository.getMovieRecommendations(movieId)
        emit(data)
    }

    //to get a movie trailer
    val movieTrailersLiveData : LiveData<List<Result>> = liveData {
        val data =  moviesRepository.getMovieTrailer(movieId)
        emit(data)
    }

    suspend fun getMovieFromDb (movieId:Long) = moviesRepository.getMovie(movieId)

    suspend fun insertMovieToDb (model: FavoriteMovieModel) = moviesRepository.insertMovie(model)

    suspend fun deleteMovieFromDb (model: FavoriteMovieModel) = moviesRepository.deleteMovie(model)

}