package models

data class MovieTrailerModel (val id:Int, val results:List<Result>)

data class Result (val key:String)
