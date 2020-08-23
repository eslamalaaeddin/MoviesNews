package fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnews.R
import com.example.moviesnews.Utils
import viewmodels.TopRatedMoviesViewModel

private const val TAG = "TopRatedMoviesFragment"
class TopRatedMoviesFragment : Fragment() {
    private lateinit var generalView: View
    private lateinit var topRatedMoviesViewModel: TopRatedMoviesViewModel
    private lateinit var topRatedMoviesAdapter: PopularMoviesFragment.MoviesAdapter
    private lateinit var topRatedMoviesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topRatedMoviesViewModel = ViewModelProviders.of(this).get(TopRatedMoviesViewModel::class.java)

    }
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

        topRatedMoviesRecyclerView = view.findViewById(R.id.movies_recycler_view)

        topRatedMoviesRecyclerView.layoutManager =  LinearLayoutManager(context)

        //to check the network connection
        if (Utils.checkConnectivity(requireContext())) {
            topRatedMoviesViewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner, Observer {
                topRatedMoviesAdapter = PopularMoviesFragment.MoviesAdapter(it.shuffled())
                topRatedMoviesRecyclerView.adapter = topRatedMoviesAdapter
            })
        }

        else{
            Toast.makeText(requireContext(), "No internet connection!", Toast.LENGTH_SHORT).show()
        }

    }


}