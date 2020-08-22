package com.example.moviesnews


private const val MOVIE_ID = "movie"
object Utils {

    var itemId = 0

    fun setMovieDataForIntent(itemId:Int) {
        this.itemId = itemId
    }

}
