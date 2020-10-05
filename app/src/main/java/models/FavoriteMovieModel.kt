package models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovieModel (@PrimaryKey val movieId:Int, val posterPath:String) {
}