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
import kotlinx.android.synthetic.main.fragment_movies_rated_top.*
import viewmodels.TopRatedMoviesViewModel

private const val TAG = "TopRatedMoviesFragment"
@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment() {
    private lateinit var generalView: View
    private val topRatedMoviesViewModel : TopRatedMoviesViewModel by viewModels()
    private var topRatedMoviesAdapter: MoviesAdapter = MoviesAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generalView = View.inflate(context, R.layout.fragment_movies_rated_top, null)
        return generalView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            topRatedMoviesViewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner, Observer {
                topRatedMoviesAdapter = MoviesAdapter(it.shuffled())
                movies_recycler_view.adapter = topRatedMoviesAdapter
            })
    }


}