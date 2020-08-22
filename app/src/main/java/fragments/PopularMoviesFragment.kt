package fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnews.Callback
import com.example.moviesnews.Movie
import com.example.moviesnews.R
import com.example.moviesnews.Utils
import com.squareup.picasso.Picasso
import viewmodels.PopularMoviesViewModel

private const val TAG = "PopularMoviesFragment"
lateinit var callback: Callback
class PopularMoviesFragment () : Fragment() {
    private lateinit var generalView: View
    private lateinit var popularMoviesViewModel: PopularMoviesViewModel
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesRecyclerView: RecyclerView




    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as Callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularMoviesViewModel = ViewModelProviders.of(this).get(PopularMoviesViewModel::class.java)
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

        popularMoviesRecyclerView = view.findViewById(R.id.movies_recycler_view)

        popularMoviesRecyclerView.layoutManager =  LinearLayoutManager(context)

        popularMoviesViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
                popularMoviesAdapter = MoviesAdapter(it)
                popularMoviesRecyclerView.adapter = popularMoviesAdapter
        })


    }

    class MoviesAdapter (private val movies:List<Movie>) :
        RecyclerView.Adapter<MoviesAdapter.PopularMoviesViewHolder>() {

        inner class PopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            private val movieTitleTextView: TextView = itemView.findViewById(R.id.movie_title_text_view)
            private val movieRatingTextView: TextView = itemView.findViewById(R.id.movie_rating)
            private val movieReleaseDateTextView: TextView = itemView.findViewById(R.id.movie_release_date)
            private val movieLanguageTextView: TextView = itemView.findViewById(R.id.movie_language)
            private val movieImageView: ImageView = itemView.findViewById(R.id.movie_image_view)

            init {
                itemView.setOnClickListener(this)
            }

            @SuppressLint("SetTextI18n")
            fun bind(movie: Movie) {
                movieTitleTextView.text = movie.title
                movieRatingTextView.text = "${movie.vote_average}/10"
                movieReleaseDateTextView.text = movie.release_date
                movieLanguageTextView.text = movie.original_language

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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
            val cardView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false) as CardView
            return PopularMoviesViewHolder(cardView)
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
            val movie = movies[holder.adapterPosition]
            holder.bind(movie)
        }
    }



}