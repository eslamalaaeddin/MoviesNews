package models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovieModel (@PrimaryKey val movieId:Long, val posterPath:String) {
}