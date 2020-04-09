package com.example.tmdbdemo

import java.io.Serializable

class Movie(var title:String,var overview:String,var releaseDate:String,var imageUrl:String) :Serializable{

    companion object{
        private const val  serialVersionUID=1L
    }

    override fun toString(): String {
        return "Movie(title='$title', overview='$overview', releaseDate='$releaseDate', imageUrl='$imageUrl')"
    }



}