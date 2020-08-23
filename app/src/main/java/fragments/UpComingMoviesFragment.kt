package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnews.R
import viewmodels.UpComingMoviesViewModel

private const val TAG = "UpComingMoviesFragment"
class UpComingMoviesFragment : Fragment() {
    private lateinit var generalView: View
    private lateinit var upComingMoviesViewModel: UpComingMoviesViewModel
    private lateinit var upComingMoviesAdapter: PopularMoviesFragment.MoviesAdapter
    private lateinit var upComingMoviesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        upComingMoviesViewModel = ViewModelProviders.of(this).get(UpComingMoviesViewModel::class.java)
    }
    
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


        upComingMoviesRecyclerView = view.findViewById(R.id.movies_recycler_view)

        upComingMoviesRecyclerView.layoutManager =  LinearLayoutManager(context)

        upComingMoviesViewModel.upComingMoviesLiveData.observe(viewLifecycleOwner, Observer {
            upComingMoviesAdapter = PopularMoviesFragment.MoviesAdapter(it.shuffled())
            upComingMoviesRecyclerView.adapter = upComingMoviesAdapter
        })
    }
}