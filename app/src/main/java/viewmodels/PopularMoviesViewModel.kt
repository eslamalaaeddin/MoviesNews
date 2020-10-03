package viewmodels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import models.Movie
import com.example.moviesnews.MoviesRepository

private const val TAG = "PopularMoviesViewModel"
class PopularMoviesViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val popularMoviesLiveData : LiveData<List<Movie>> = liveData {
        val data = moviesRepository.getPopularMovies()
        emit(data)
    }

    }

