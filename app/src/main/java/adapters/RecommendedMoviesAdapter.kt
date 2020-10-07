package adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnews.R
import com.squareup.picasso.Picasso
import models.Movie
import views.fragments.callback

class RecommendedMoviesAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<RecommendedMoviesAdapter.PopularMoviesViewHolder>() {

    inner class PopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val movieImageView: ImageView = itemView.findViewById(R.id.movie_image_view)

        init {
            itemView.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(movie: Movie) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.recommended_movie_item,parent,false) as CardView
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