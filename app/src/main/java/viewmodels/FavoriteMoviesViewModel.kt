package viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import models.DetailedMovie
import com.example.moviesnews.MoviesRepository
import models.FavoriteMovieModel

class FavoriteMoviesViewModel @ViewModelInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel()  {



//    val favoriteMoviesLiveData : LiveData<List<FavoriteMovieModel>> = liveData {
//        val data = moviesRepository.getMovies()
//        emit(data)
//    }

    val favoriteMoviesLiveData = moviesRepository.getMovies()

}