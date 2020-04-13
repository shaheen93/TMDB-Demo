package com.example.tmdbdemo

import java.io.Serializable

data class Film(
    val popularity: Double,
    val vote_count: Int,
    val video: Boolean,
    val poster_path: String?,
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String?,
    val original_language: String,
    val genre_ids: List<Int>,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }

    override fun toString(): String {
        return "Film(" +
                "     popularity=$popularity," +
                "     vote_count=$vote_count," +
                "     video=$video," +
                "     poster_path=$poster_path," +
                "     id=$id," +
                "     adult=$adult," +
                "     backdrop_path=$backdrop_path" +
                "     original_language=$original_language" +
                "     genre_ids=$genre_ids" +
                "     title=$title" +
                "     vote_average=$vote_average" +
                "     overview=$overview" +
                "     release_date=$release_date" +
                ") "
    }
}