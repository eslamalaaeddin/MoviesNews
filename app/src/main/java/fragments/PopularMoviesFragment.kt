package fragments

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
import com.example.moviesnews.Movie
import com.example.moviesnews.R
import com.squareup.picasso.Picasso
import viewmodels.PopularMoviesViewModel

private const val TAG = "PopularMoviesFragment"
class PopularMoviesFragment () : Fragment() {
    private lateinit var generalView: View
    private lateinit var popularMoviesViewModel: PopularMoviesViewModel
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var popularMoviesRecyclerView: RecyclerView

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

        popularMoviesRecyclerView = view.findViewById(R.id.popular_movies_recycler_view)

        popularMoviesRecyclerView.layoutManager =  LinearLayoutManager(context)


        popularMoviesViewModel.popularMoviesLiveData.observe(viewLifecycleOwner, Observer {
                popularMoviesAdapter = PopularMoviesAdapter(it)
                popularMoviesRecyclerView.adapter = popularMoviesAdapter
        })
    }

    class PopularMoviesAdapter (private val popularMovies:List<Movie>) :
        RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder>() {

        inner class PopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val movieTitleTextView = itemView.findViewById<TextView>(R.id.movie_title)
          val movieRatingTextView = itemView.findViewById<TextView>(R.id.movie_rating)
          val movieReleaseDateTextView = itemView.findViewById<TextView>(R.id.movie_release_date)
          val movieLanguageTextView = itemView.findViewById<TextView>(R.id.movie_language)
          val movieImageView = itemView.findViewById<ImageView>(R.id.movie_image_view)
          val moviewFavoriteImageView = itemView.findViewById<ImageView>(R.id.movie_favorite_image_view)

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

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
            val cardView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false) as CardView
            return PopularMoviesViewHolder(cardView)
        }

        override fun getItemCount(): Int {
            return popularMovies.size
        }

        override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
            val movie = popularMovies[holder.adapterPosition]
            holder.bind(movie)
        }
    }
}