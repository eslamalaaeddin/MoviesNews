package com.example.moviesnews

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fragments.callback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.DetailedMovie
import models.Model
import models.Movie
import viewmodels.MovieViewModel

private const val TAG = "MovieActivity"
class MovieActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var recommendedMoviesAdapter: MoviesAdapter
    private lateinit var recommendationsRecyclerView: RecyclerView

    private  var movie: DetailedMovie?= null
    private lateinit var model: Model

    private lateinit var backPosterImageView: ImageView
    private lateinit var frontPosterImageView: ImageView
    private lateinit var favoriteImageView: ImageView
    private lateinit var movieTitleTextView:TextView
    private lateinit var movieReleaseDateTextView:TextView
    private lateinit var movieDurationTextView:TextView
    private lateinit var movieOverviewTextView:TextView
    private lateinit var movieRatingTextView:TextView

    private lateinit var watchTrailerButton:Button
    private lateinit var moreAboutButton: Button

    var inDb = false
    var returned: Model? = null
    private var key = ""

    private var movieId = 0
    private var frontPosterPath = ""
    private var frontPosterUrl = ""
    private var backPosterPath = ""
    private var backPosterUrl = ""
    private var title = ""
    private var releaseDate = ""
    private var overview = ""
    private var homePage = ""
    private var rating = 0.0
    private var duration = 0

    val repository = MoviesRepository()

    val scope = CoroutineScope(Dispatchers.Main)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        backPosterImageView = findViewById(R.id.back_poster_image_view)
        frontPosterImageView = findViewById(R.id.front_poster_image_view)
        favoriteImageView = findViewById(R.id.favorite_image_view)
        movieTitleTextView = findViewById(R.id.movie_title_text_view)
        movieReleaseDateTextView = findViewById(R.id.movie_release_date_text_view)
        movieDurationTextView = findViewById(R.id.movie_duration_text_view)
        movieOverviewTextView = findViewById(R.id.movie_overview_text_view)
        movieRatingTextView = findViewById(R.id.moview_rating_text_view)
        watchTrailerButton = findViewById(R.id.watch_trailer_button)
        moreAboutButton = findViewById(R.id.more_about_button)

        //recycler view
        recommendationsRecyclerView = findViewById(R.id.movies_recycler_view)
        recommendationsRecyclerView.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        //to get the movie details
        movieViewModel.detailedMovieLiveData.observe(this , Observer {
            movieId = it.id
            backPosterPath = it.backdrop_path
            frontPosterPath = it.poster_path
            title = it.title
            homePage = it.homepage
            rating = it.vote_average
            overview = it.overview
            releaseDate = it.release_date
            duration = it.runtime


            frontPosterUrl = "https://image.tmdb.org/t/p/w500".plus(frontPosterPath)
            backPosterUrl  = "https://image.tmdb.org/t/p/w500".plus(backPosterPath)

            movieTitleTextView.text = title
            movieRatingTextView.text = rating.toString()
            movieReleaseDateTextView.text = releaseDate
            movieDurationTextView.text = "$duration minutes"
            movieOverviewTextView.text = overview

            Picasso.get()
                .load(frontPosterUrl)
                .placeholder(R.drawable.ic_loading)
                .into(frontPosterImageView)

            Picasso.get()
                .load(backPosterUrl)
                .placeholder(R.drawable.ic_loading)
                .into(backPosterImageView)
        })
        //to get a movie recommendations
        movieViewModel.recommendedMoviesLiveData.observe(this, Observer {
            recommendedMoviesAdapter = MoviesAdapter(it)
            recommendationsRecyclerView.adapter = recommendedMoviesAdapter
        })
        //to get the key for the trailer video
        movieViewModel.movieTrailersLiveData.observe(this, Observer {
            if (it.isNotEmpty()) {
                key = it[0].key
            }
            else{
                key = ""
            }
        })

        //open youtube
        watchTrailerButton.setOnClickListener {
            if (key!="" && key!=null){
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$key"))
                )
            }
            else{
                Toast.makeText(this, "No trailer available!", Toast.LENGTH_SHORT).show()
            }

        }

        //open the movie's site
        moreAboutButton.setOnClickListener {
            if (homePage== null || homePage == "" ) {
                Toast.makeText(this, "No homepage yet!", Toast.LENGTH_SHORT).show()
            }
            else {
                if (homePage != "") {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(homePage))
                    val browserChooserIntent =
                        Intent.createChooser(browserIntent, "Choose browser of your choice")
                    startActivity(browserChooserIntent)
                }
            }

        }

        //favorite image view
        favoriteImageView.setOnClickListener{

            scope.launch {
                var returned : Model = repository.getMovie(movieId)

                if (returned != null) {
                   // returned = Model(movieId, 0)
                    repository.deleteMovie(returned)
                    favoriteImageView.setColorFilter(
                        ContextCompat.getColor(this@MovieActivity, R.color.unFavorite),
                        android.graphics.PorterDuff.Mode.SRC_IN)
                    Toast.makeText(this@MovieActivity, "Removed from favorites.", Toast.LENGTH_SHORT).show()
                }
                else{
                    returned = Model(movieId, 1)
                    repository.insertMovie(returned)
                    favoriteImageView.setColorFilter(
                        ContextCompat.getColor(this@MovieActivity, R.color.favorite),
                        android.graphics.PorterDuff.Mode.SRC_IN)
                    Toast.makeText(this@MovieActivity, "Added to favorites.", Toast.LENGTH_SHORT).show()

                }
            }
                }

        }


    override fun onStart() {
        super.onStart()
        scope.launch {
            val returned : Model = repository.getMovie(Utils.itemId)

            if (returned != null) {

            if (returned.favorite == 0) {
                favoriteImageView.setColorFilter(
                    ContextCompat.getColor(this@MovieActivity, R.color.unFavorite),
                    android.graphics.PorterDuff.Mode.SRC_IN)
            } else {
                favoriteImageView.setColorFilter(
                    ContextCompat.getColor(this@MovieActivity, R.color.favorite),
                    android.graphics.PorterDuff.Mode.SRC_IN)
            }
            }

        }


    }

    class MoviesAdapter(private val movies: List<Movie>) :
        RecyclerView.Adapter<MoviesAdapter.PopularMoviesViewHolder>() {

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
                Utils.setMovieDataForIntent(movie.id)
                callback.onMovieClicked()
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

}
