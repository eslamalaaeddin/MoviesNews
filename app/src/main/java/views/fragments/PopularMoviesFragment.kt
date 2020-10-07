package views.fragments

import adapters.ApiMoviesAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.moviesnews.BaseApplication
import com.example.moviesnews.Callback
import com.example.moviesnews.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie.*
import viewmodels.PopularMoviesViewModel

private const val TAG = "PopularMoviesFragment"
lateinit var callback: Callback
@AndroidEntryPoint
class PopularMoviesFragment () : Fragment() {
    private lateinit var generalView: View
    private val popularMoviesViewModel: PopularMoviesViewModel by viewModels()
    private var popularMoviesAdapter: ApiMoviesAdapter = ApiMoviesAdapter(emptyList())

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generalView = View.inflate(context , R.layout.fragment_movies_popular, null)
        return generalView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (BaseApplication.checkConnectivity(requireContext())) {
            popularMoviesViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
                popularMoviesAdapter = ApiMoviesAdapter(it.shuffled())
                movies_recycler_view.adapter = popularMoviesAdapter
            })
        }

        else{
            Toast.makeText(context, "No internet connection.", Toast.LENGTH_SHORT).show()
        }




    }



}