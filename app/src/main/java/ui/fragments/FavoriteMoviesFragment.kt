package ui.fragments

import adapters.FavoriteMoviesAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnews.*
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies_favorite.*
import models.DetailedMovie
import viewmodels.FavoriteMoviesViewModel

private const val TAG = "FavoriteMoviesFragment"
@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {
    private var favoriteMoviesAdapter: FavoriteMoviesAdapter = FavoriteMoviesAdapter(emptyList())
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
            favoriteMoviesAdapter = FavoriteMoviesAdapter(it)
            movies_recycler_view.adapter = favoriteMoviesAdapter
        })

    }



}