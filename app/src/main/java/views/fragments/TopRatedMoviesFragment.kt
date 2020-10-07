package views.fragments

import adapters.ApiMoviesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.moviesnews.BaseApplication
import com.example.moviesnews.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.fragment_movies_rated_top.*
import kotlinx.android.synthetic.main.fragment_movies_rated_top.movies_recycler_view
import viewmodels.TopRatedMoviesViewModel

private const val TAG = "TopRatedMoviesFragment"
@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment() {
    private lateinit var generalView: View
    private val topRatedMoviesViewModel : TopRatedMoviesViewModel by viewModels()
    private var topRatedMoviesAdapter: ApiMoviesAdapter = ApiMoviesAdapter(emptyList())

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

        if (BaseApplication.checkConnectivity(requireContext())) {
            topRatedMoviesViewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner, Observer {
                topRatedMoviesAdapter = ApiMoviesAdapter(it.shuffled())
                movies_recycler_view.adapter = topRatedMoviesAdapter
            })
        }

        else{
            Toast.makeText(context, "No internet connection.", Toast.LENGTH_SHORT).show()
        }


    }
}