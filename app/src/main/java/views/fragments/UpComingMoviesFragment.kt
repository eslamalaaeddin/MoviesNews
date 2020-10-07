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
import kotlinx.android.synthetic.main.activity_movie.movies_recycler_view
import viewmodels.UpComingMoviesViewModel

private const val TAG = "UpComingMoviesFragment"
@AndroidEntryPoint
class UpComingMoviesFragment : Fragment() {
    private lateinit var generalView: View
    private val upComingMoviesViewModel: UpComingMoviesViewModel by viewModels()
    private var upComingMoviesAdapter: ApiMoviesAdapter = ApiMoviesAdapter(emptyList())

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

        if (BaseApplication.checkConnectivity(requireContext())) {
            upComingMoviesViewModel.upComingMoviesLiveData.observe(viewLifecycleOwner, Observer {
                upComingMoviesAdapter = ApiMoviesAdapter(it.shuffled())
                movies_recycler_view.adapter = upComingMoviesAdapter
            })
        }

        else{
            Toast.makeText(context, "No internet connection.", Toast.LENGTH_SHORT).show()
        }


    }
}