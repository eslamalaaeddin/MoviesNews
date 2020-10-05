package models

data class MovieTrailerModel (val id:Long, val results:List<Result>)

data class Result (val key:String)
