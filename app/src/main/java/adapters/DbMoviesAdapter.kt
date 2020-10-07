package adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesnews.R
import com.squareup.picasso.Picasso
import models.FavoriteMovieModel
import views.fragments.callback


    class DbMoviesAdapter(private val movies: List<FavoriteMovieModel>) :
        RecyclerView.Adapter<DbMoviesAdapter.MoviesViewHolder>() {

        inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            private val movieImageView: ImageView = itemView.findViewById(R.id.movie_image_view)

            init {
                itemView.setOnClickListener(this)
            }

            @SuppressLint("SetTextI18n")
            fun bind(movie: FavoriteMovieModel) {

                val imageUrl = movie.posterPath

                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_loading)
                    .into(movieImageView)
            }

            override fun onClick(item: View?) {
                val movie = movies[adapterPosition]
                callback.onMovieClicked(movie.movieId)
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
