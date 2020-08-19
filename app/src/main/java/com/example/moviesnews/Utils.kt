package com.example.moviesnews

import android.content.Context
import android.preference.PreferenceManager

private const val MOVIE_ID = "movie"
object Utils {

    var itemId = 0
    var posterPath = ""
    var backdropPath = ""
    var title = ""
    var releaseDate = ""
    var overview = ""

    fun getFavorites(context: Context?): Int? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(MOVIE_ID, 0)
    }

    fun setFavorites(context: Context?, movieId: Int) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putInt(MOVIE_ID, movieId)
            .apply()
    }

    fun setMovieDataForIntent(itemId:Int,
                              posterPath:String,
                              backdropPath:String,
                              title:String,
                              releaseDate:String,
                              overview:String ) {

        this.itemId = itemId
        this.posterPath = posterPath
        this.backdropPath = backdropPath
        this.title = title
        this.releaseDate = releaseDate
        this.overview = overview

    }



}
