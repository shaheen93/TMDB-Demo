package com.example.tmdbdemo

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel:ViewModel() {


    private val _movies = MutableLiveData<List<Film>>()
    val movies: LiveData<List<Film>>
        get() = _movies

    fun getMovies(p:Int,d:String){
        MoviesApi().getMovieList(p,d).enqueue(object : Callback<FilmList> {
            override fun onFailure(call: Call<FilmList>, t: Throwable) {
                println(t)
            }

            override fun onResponse(call: Call<FilmList>, response: Response<FilmList>) {
                val films = response.body()
                if (films != null) {
                    _movies.value=films.results
                }
            }
        })
    }


}