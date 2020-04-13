package com.example.tmdbdemo

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

val url ="https://api.themoviedb.org/3/discover/"


interface MoviesApi {
    @GET("https://api.themoviedb.org/3/discover/movie?api_key=6ceffecc92ef62d38f4d80eb3d3883d3&language=en&sort_by=primary_release_date.desc")
    fun getMovieList(@Query("page") page: Int,@Query("primary_release_date.lte") date:String): Call<FilmList>

    companion object {
        operator fun invoke(): MoviesApi {

            val retrofit=Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(MoviesApi::class.java)
            Log.d("URLUSED",Retrofit.Builder().baseUrl(url).build().toString())
            return retrofit
            }
    }

}