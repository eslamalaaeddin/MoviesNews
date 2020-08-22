package fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnews.*
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "FavoriteMoviesFragment"
class FavoriteMoviesFragment : Fragment() {
    private lateinit var favoriteMoviesAdapter: MoviesAdapter
    private lateinit var favoriteMoviesRecyclerView: RecyclerView

   // private lateinit var favoriteMoviesViewModel: FavoriteMoviesViewModel

    val favoriteList = mutableListOf<Model>()
    var favoriteMovies = mutableListOf<DetailedMovie>()
    private val repo = MoviesRepository()
    val scope = CoroutineScope(Dispatchers.Main)
    private var postsList = mutableListOf<DetailedMovie>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // favoriteMoviesViewModel = ViewModelProviders.of(this).get(FavoriteMoviesViewModel::class.java)
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

        Log.i(TAG, "onViewCreated: ")

    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
        scope.launch {
            favoriteList.clear()
            postsList.clear()
            favoriteList.addAll(repo.getMovies())
            postsList.addAll(getFavoriteMovies(favoriteList))
            favoriteMoviesAdapter = MoviesAdapter(postsList.distinct())
            favoriteMoviesRecyclerView.adapter = favoriteMoviesAdapter

        }
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
        favoriteList.clear()
        postsList.clear()
    }

    private suspend fun getFavoriteMovies (models:List<Model>) : List<DetailedMovie>  {
        for (element in models) {
                favoriteMovies.add(repo.getFavoriteMovies(element.movieId))
        }

       return favoriteMovies
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