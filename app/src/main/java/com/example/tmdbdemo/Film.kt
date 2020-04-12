package com.example.tmdbdemo

import java.io.Serializable

class Film (
    val popularity:Double,
    val vote_count:Int,
    val video:Boolean,
    val poster_path:String,
    val id:Int,
    val adult:Boolean,
    val backdrop_path:String,
    val original_language:String,
    val genre_ids:Map<String,Int>,
    val title:String,
    val vote_average:Double,
    val overview:String,
    val release_date:String
):Serializable{
    companion object{
        private const val  serialVersionUID=1L
    }


}