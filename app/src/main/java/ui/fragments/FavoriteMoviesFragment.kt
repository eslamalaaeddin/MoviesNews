package ui.fragments

import adapters.DbMoviesAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.moviesnews.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies_favorite.*
import viewmodels.FavoriteMoviesViewModel

private const val TAG = "FavoriteMoviesFragment"
@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {
    /*
        The reason why i have created two adapters is that the movie model in each movie list, either in the
        favorites or in the movies from api is different.
     */
    private var favoriteMoviesAdapter: DbMoviesAdapter = DbMoviesAdapter(emptyList())
    private val favoriteMoviesViewModel: FavoriteMoviesViewModel by viewModels()

    private lateinit var generalView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generalView = View.inflate(context , R.layout.fragment_movies_favorite , null)
        return generalView
    }

    override fun onStart() {
        super.onStart()
        favoriteMoviesViewModel.favoriteMoviesLiveData.observe(viewLifecycleOwner, Observer {
            favoriteMoviesAdapter = DbMoviesAdapter(it)
            movies_recycler_view.adapter = favoriteMoviesAdapter
        })
    }




}