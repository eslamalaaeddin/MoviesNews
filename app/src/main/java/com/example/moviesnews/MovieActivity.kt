package com.example.moviesnews

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import viewmodels.MovieViewModel

private const val ID = "id"
private const val POSTER_PATH = "posterPath"
private const val BACK_DROP_PATH = "backdropPath"
private const val TITLE = "title"
private const val RELEASE_DATE = "releaseDate"
private const val OVERVIEW = "overview"
private const val TAG = "MovieActivity"
class MovieActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel
    private  var movie:DetailedMovie ?= null

    private lateinit var backPosterImageView: ImageView
    private lateinit var frontPosterImageView: ImageView
    private lateinit var movieTitleTextView:TextView
    private lateinit var movieReleaseDateTextView:TextView
    private lateinit var movieDurationTextView:TextView
    private lateinit var movieOverviewTextView:TextView

    private lateinit var watchTrailerButton:Button
    private lateinit var moreImagesButton: Button

    private lateinit var recommendationsRecyclerView: RecyclerView

    private var itemId = 0
    private var frontPosterPath = ""
    private var frontPosterUrl = ""
    private var backPosterPath = ""
    private var backPosterUrl = ""
    private var title = ""
    private var releaseDate = ""
    private var overview = ""
    private var duration = 0


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        itemId = intent.getIntExtra(ID,0)

        backPosterImageView = findViewById(R.id.back_poster_image_view)
        frontPosterImageView = findViewById(R.id.front_poster_image_view)
        movieTitleTextView = findViewById(R.id.movie_title_text_view)
        movieReleaseDateTextView = findViewById(R.id.movie_release_date_text_view)
        movieDurationTextView = findViewById(R.id.movie_duration_text_view)
        movieOverviewTextView = findViewById(R.id.movie_overview_text_view)

        movieViewModel.detailedMovieLiveData.observe(this , Observer {
            backPosterPath = it.backdrop_path
            frontPosterPath = it.poster_path
            title = it.title
            overview = it.overview
            releaseDate = it.release_date

            frontPosterUrl = "https://image.tmdb.org/t/p/w500".plus(frontPosterPath)
            backPosterUrl  = "https://image.tmdb.org/t/p/w500".plus(backPosterPath)

            movieTitleTextView.text = title
            movieReleaseDateTextView.text = releaseDate
            movieDurationTextView.text = "${it.runtime} minutes"
            movieOverviewTextView.text = overview

            Picasso.get()
                .load(frontPosterUrl)
                .placeholder(R.drawable.ic_loading)
                .into(frontPosterImageView)

            Picasso.get()
                .load(backPosterUrl)
                .placeholder(R.drawable.ic_loading)
                .into(backPosterImageView)
            Log.i(TAG, "onCreate: ISLAM $movie")
        })
    }


    private fun extractDataFromIntent(){
        itemId = intent.getIntExtra(ID,0)
        frontPosterPath = intent.getStringExtra(POSTER_PATH).toString()
        backPosterPath = intent.getStringExtra(BACK_DROP_PATH).toString()
        title = intent.getStringExtra(TITLE).toString()
        releaseDate = intent.getStringExtra(RELEASE_DATE).toString()
        overview = intent.getStringExtra(OVERVIEW).toString()
    }

}