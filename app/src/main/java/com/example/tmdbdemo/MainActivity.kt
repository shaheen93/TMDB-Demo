package com.example.tmdbdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), RecyclerItemClickListener.OnRecyclerClickListener {
    private val movieListAdapter = RecyclerViewAdapter(ArrayList())
    private val movieLayoutManager = LinearLayoutManager(this)
    var isLoading = false
    private var page = 0
    var date: String = SimpleDateFormat("yyyy-MM-dd").format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movie_list.layoutManager = movieLayoutManager
        movie_list.addOnItemTouchListener(RecyclerItemClickListener(this, movie_list, this))
        movie_list.adapter = movieListAdapter
        page = 1

        Log.d("dateToday", date)

        val viewModel= ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.movies.observe(this,Observer<List<Film>>{movies->movieListAdapter.loadNewData(movies)})

        viewModel.getMovies(page,date)

//        getItems()

        movie_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {


                val visibleItemCount = movieLayoutManager.childCount
                val pastVisibleItem = movieLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = movieListAdapter.itemCount

                if (!isLoading) {

                    if ((visibleItemCount + pastVisibleItem) > total) {
                        page++
                        isLoading = true
                        progressBar.visibility = View.VISIBLE

                        viewModel.getMovies(page,date)
//                        getItems()
                        isLoading = false
                        progressBar.visibility = View.GONE

                    }

                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })


    }


    override fun onItemClick(view: View, position: Int) {
        val movie = movieListAdapter.getMovie(position)
        if (movie != null) {
            val intent = Intent(this, MovieDetails::class.java)
            intent.putExtra("Film", movie)
            startActivity(intent)
        }
    }

    fun getItems(){
        MoviesApi().getMovieList(page, date).enqueue(object : Callback<FilmList> {
            override fun onFailure(call: Call<FilmList>, t: Throwable) {
                println(t)
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<FilmList>, response: Response<FilmList>) {
                val films = response.body()
                if (films != null) {
                    movieListAdapter.loadNewData(films.results)
                }
            }
        })
    }

}
