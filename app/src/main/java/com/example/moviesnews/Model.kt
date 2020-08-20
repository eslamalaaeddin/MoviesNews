package com.example.moviesnews

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Model (@PrimaryKey val movieId:Int, val favorite:Int = 0) {
}