package viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import models.Movie
import com.example.moviesnews.MoviesRepository

private const val TAG = "PopularMoviesViewModel"
class PopularMoviesViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()

    val popularMoviesLiveData : LiveData<List<Movie>> = liveData {
        val data = moviesRepository.getPopularMovies()
        emit(data)
    }

    }

