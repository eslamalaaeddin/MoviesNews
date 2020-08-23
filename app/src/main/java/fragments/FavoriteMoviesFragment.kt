package fragments

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnews.*
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import viewmodels.FavoriteMoviesViewModel

private const val TAG = "FavoriteMoviesFragment"
class FavoriteMoviesFragment : Fragment() {
    private lateinit var favoriteMoviesAdapter: MoviesAdapter
    private lateinit var favoriteMoviesRecyclerView: RecyclerView
    private lateinit var favoriteMoviesViewModel: FavoriteMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteMoviesViewModel = ViewModelProviders.of(this).get(FavoriteMoviesViewModel::class.java)
    }

    private lateinit var generalView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generalView = View.inflate(context , R.layout.fragment_movies_favorite , null)
        return generalView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteMoviesRecyclerView = view.findViewById(R.id.movies_recycler_view)
        favoriteMoviesRecyclerView.layoutManager =  GridLayoutManager(context,2)


    }



    override fun onStart() {
        super.onStart()

        favoriteMoviesViewModel.favoriteMoviesLiveData.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "onStart: ${it.size}")
            favoriteMoviesAdapter = MoviesAdapter(it)
            favoriteMoviesRecyclerView.adapter = favoriteMoviesAdapter
        })

    }

    class MoviesAdapter(private val movies: List<DetailedMovie>) :
        RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

        inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            private val movieImageView: ImageView = itemView.findViewById(R.id.movie_image_view)

            init {
                itemView.setOnClickListener(this)
            }

            @SuppressLint("SetTextI18n")
            fun bind(movie: DetailedMovie) {

                val url = "https://image.tmdb.org/t/p/w500".plus(movie.poster_path)

                Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.ic_loading)
                    .into(movieImageView)
            }

            override fun onClick(item: View?) {
                val movie = movies[adapterPosition]
                Utils.setMovieDataForIntent(movie.id)
                callback.onMovieClicked()
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
            val frameLayout = LayoutInflater.from(parent.context).inflate(R.layout.favorite_movie_item,parent,false) as FrameLayout
            return MoviesViewHolder(frameLayout)
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
            val movie = movies[holder.adapterPosition]
            holder.bind(movie)
        }
    }

}