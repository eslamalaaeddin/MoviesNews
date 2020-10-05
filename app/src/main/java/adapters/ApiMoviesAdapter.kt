package adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnews.R
import com.example.moviesnews.Utils
import com.squareup.picasso.Picasso
import models.Movie
import ui.fragments.callback


class ApiMoviesAdapter (private val movies:List<Movie>) :
        RecyclerView.Adapter<ApiMoviesAdapter.MoviesViewHolder>() {

        inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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
                callback.onMovieClicked(movie.id)
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
            val cardView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false) as CardView
            return MoviesViewHolder(cardView)
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
            val movie = movies[holder.adapterPosition]
            holder.bind(movie)
        }
    }
