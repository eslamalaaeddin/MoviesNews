package ui.fragments

import adapters.MoviesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnews.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie.movies_recycler_view
import kotlinx.android.synthetic.main.fragment_movies_coming_up.*
import viewmodels.UpComingMoviesViewModel

private const val TAG = "UpComingMoviesFragment"
@AndroidEntryPoint
class UpComingMoviesFragment : Fragment() {
    private lateinit var generalView: View
    private val upComingMoviesViewModel: UpComingMoviesViewModel by viewModels()
    private var upComingMoviesAdapter: MoviesAdapter = MoviesAdapter(emptyList())


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generalView = View.inflate(context , R.layout.fragment_movies_coming_up , null)
        return generalView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upComingMoviesViewModel.upComingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            upComingMoviesAdapter = MoviesAdapter(it.shuffled())
            movies_recycler_view.adapter = upComingMoviesAdapter
        })
    }
}