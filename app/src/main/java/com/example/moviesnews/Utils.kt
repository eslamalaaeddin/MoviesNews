package com.example.moviesnews

import android.content.Context
import android.preference.PreferenceManager

private const val MOVIE = "movie"
object Utils {

    var itemId = 0
    var posterPath = ""
    var backdropPath = ""
    var title = ""
    var releaseDate = ""
    var overview = ""

    fun getFavorites(context: Context?): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean(MOVIE, false)
    }

    fun setFavorites(context: Context?, movieState: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putBoolean(MOVIE, movieState)
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
