package viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.moviesnews.Movie
import com.example.moviesnews.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopularMoviesViewModel : ViewModel() {
    private val moviesRepository = MoviesRepository()

    val popularMoviesLiveData : LiveData<List<Movie>> = liveData {
        val data = moviesRepository.getPopularMovies()
        emit(data)
    }

    }

