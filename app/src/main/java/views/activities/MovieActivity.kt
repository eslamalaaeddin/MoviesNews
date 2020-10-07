package views.activities

import adapters.RecommendedMoviesAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesnews.BaseApplication
import com.example.moviesnews.R
//import com.example.moviesnews.Utils
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.activity_movie.movies_recycler_view
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.FavoriteMovieModel
import viewmodels.MovieViewModel

private const val TAG = "MovieActivity"
private const val ID = "id"
@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var recommendedMoviesAdapter: RecommendedMoviesAdapter

    private var key = ""
    private var movieId = 0L
    private var frontPosterUrl = ""
    private var backPosterUrl = ""
    private var homePage = ""

    private val scope = CoroutineScope(Dispatchers.Main)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        movieId = intent.getLongExtra(ID,0L)


        movies_recycler_view.layoutManager =
            LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)



        if (BaseApplication.checkConnectivity(this)) {
            watch_trailer_button.visibility = View.VISIBLE
            more_about_button.visibility = View.VISIBLE
            recommendations_text_view.visibility = View.VISIBLE
            getMovieDetails()
            //to get a movie recommendations
            movieViewModel.recommendedMoviesLiveData.observe(this, Observer {
                recommendedMoviesAdapter = RecommendedMoviesAdapter(it)
                movies_recycler_view.adapter = recommendedMoviesAdapter
            })

            //to get the key for the trailer video
            movieViewModel.movieTrailersLiveData.observe(this, Observer {
                key = if (it.isNotEmpty()) { it[0].key } else{ "" }
            })
        }

        else{
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show()
            watch_trailer_button.visibility = View.INVISIBLE
            more_about_button.visibility = View.INVISIBLE
            recommendations_text_view.visibility = View.INVISIBLE
        }



        //open youtube
        watch_trailer_button.setOnClickListener { validateTrailerAndOpenYoutube() }

        //open the movie's site
        more_about_button.setOnClickListener {validateAndOpenMovieSite()}

        //favorite image view
        favorite_image_view.setOnClickListener{onFavoriteIconClicked()}

        }

    override fun onStart() {
        super.onStart()
        getMovieFromDbAndUpdateFavoriteState()
    }

    @SuppressLint("SetTextI18n")
    private fun getMovieDetails() {
        movieViewModel.detailedMovieLiveData.observe(this , Observer { movie  ->
            movieId = movie.id
            homePage = movie.homepage
            frontPosterUrl = "https://image.tmdb.org/t/p/w500".plus(movie.poster_path)
            backPosterUrl  = "https://image.tmdb.org/t/p/w500".plus(movie.backdrop_path)

            movie_title_text_view.text = movie.title
            movie_rating_text_view.text = movie.vote_average.toString()
            movie_release_date_text_view.text = movie.release_date
            movie_duration_text_view.text = "${movie.runtime} minutes"
            movie_overview_text_view.text = movie.overview

            Picasso.get()
                .load(frontPosterUrl)
                .placeholder(R.drawable.ic_loading)
                .into(front_poster_image_view)

            Picasso.get()
                .load(backPosterUrl)
                .placeholder(R.drawable.ic_loading)
                .into(back_poster_image_view)
        })
    }

    private suspend fun removeMovieFromDbAndHintUser(favoriteMovie: FavoriteMovieModel) {
        movieViewModel.deleteMovieFromDb(favoriteMovie)
        favorite_image_view.setColorFilter(
            ContextCompat.getColor(this@MovieActivity, R.color.unFavorite),
            android.graphics.PorterDuff.Mode.SRC_IN)
        Toast.makeText(this@MovieActivity, "Removed from favorites.", Toast.LENGTH_SHORT).show()
    }

    private suspend fun insertMovieToDbAndHintUser() {
       val favoriteMovie = FavoriteMovieModel(movieId,frontPosterUrl)
        movieViewModel.insertMovieToDb(favoriteMovie)
        favorite_image_view.setColorFilter(
            ContextCompat.getColor(this@MovieActivity, R.color.favorite),
            android.graphics.PorterDuff.Mode.SRC_IN)
        Toast.makeText(this@MovieActivity, "Added to favorites.", Toast.LENGTH_SHORT).show()
    }

    private fun changeFavoriteIconColorToRed() {
        favorite_image_view.setColorFilter(
            ContextCompat.getColor(this@MovieActivity, R.color.favorite),
            android.graphics.PorterDuff.Mode.SRC_IN)
    }

    private fun changeFavoriteIconColorToGrey() {
        favorite_image_view.setColorFilter(
            ContextCompat.getColor(this@MovieActivity, R.color.unFavorite),
            android.graphics.PorterDuff.Mode.SRC_IN)
    }

    private fun getMovieFromDbAndUpdateFavoriteState() {
        scope.launch {
            val returned : FavoriteMovieModel? = movieViewModel.getMovieFromDb(movieId)

            if (returned != null) {
                changeFavoriteIconColorToRed()
            }
            else{
                changeFavoriteIconColorToGrey()
            }

        }
    }

    private fun onFavoriteIconClicked() {
        scope.launch {
            val favoriteMovie : FavoriteMovieModel? = movieViewModel.getMovieFromDb(movieId)

            if (favoriteMovie != null)
            { removeMovieFromDbAndHintUser(favoriteMovie) }

            else{ insertMovieToDbAndHintUser() }
        }
    }

    private fun validateAndOpenMovieSite(){
        if (homePage== null || homePage == "" ) {
            Toast.makeText(this, "No homepage yet!", Toast.LENGTH_SHORT).show()
        }
        else {
            if (homePage != "") {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(homePage))
                val browserChooserIntent =
                    Intent.createChooser(browserIntent, "Open with")
                startActivity(browserChooserIntent)
            }
        }
    }

    private fun validateTrailerAndOpenYoutube() {
        if (key!="" && key!=null){
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$key"))
            )
        }
        else{
            Toast.makeText(this, "No trailer available!", Toast.LENGTH_SHORT).show()
        }
    }


}
