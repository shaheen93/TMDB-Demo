package com.example.tmdbdemo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity(),DownloadData.OnDownloadComplete,GetJsonData.OnDataAvailable,RecyclerItemClickListener.OnRecyclerClickListener {
    private val movieListAdapter=RecyclerViewAdapter(ArrayList())
    private val movieLayoutManager=LinearLayoutManager(this)
    private var page=0
    @RequiresApi(Build.VERSION_CODES.O)
    private var date=LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    var isLoading = false


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movie_list.layoutManager=movieLayoutManager
        movie_list.addOnItemTouchListener(RecyclerItemClickListener(this,movie_list,this))
        movie_list.adapter=movieListAdapter
        page=1

//        val downloadData=DownloadData(this)
//        downloadData.execute(" https://api.themoviedb.org/3/discover/movie?api_key=6ceffecc92ef62d38f4d80eb3d3883d3&language=en&sort_by=primary_release_date.desc&page=$page&primary_release_date.lte=$date")

        MoviesApi().getMovieList(page,date).enqueue(object :Callback<List<Film>>{
            override fun onFailure(call: Call<List<Film>>, t: Throwable) {
                Toast.makeText(applicationContext,"Download Failed",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Film>>, response: Response<List<Film>>) {
                val films=response.body()
                if (films != null) {
                    movieListAdapter.loadNewData(films)
                }
            }
        })

        movie_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {


                val visibleItemCount = movieLayoutManager.childCount
                val pastVisibleItem = movieLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total =movieListAdapter.itemCount

                if (!isLoading) {

                    if ((visibleItemCount + pastVisibleItem) > total) {
                        page++
                        isLoading = true
                        progressBar.visibility = View.VISIBLE
//                        val downloadMoreData=DownloadData(this@MainActivity)
//                        downloadMoreData.execute("https://api.themoviedb.org/3/discover/movie?api_key=6ceffecc92ef62d38f4d80eb3d3883d3&language=en&sort_by=primary_release_date.desc&page=$page&primary_release_date.lte=$date")

                        MoviesApi().getMovieList(page,date).enqueue(object :Callback<List<Film>>{
                            override fun onFailure(call: Call<List<Film>>, t: Throwable) {
                                Toast.makeText(applicationContext,"Download Failed",Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<List<Film>>, response: Response<List<Film>>) {
                                val films=response.body()
                                if (films != null) {
                                    movieListAdapter.loadNewData(films)
                                }
                            }
                        })


                        isLoading = false
                        progressBar.visibility = View.GONE

                    }

                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })


    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if(status==DownloadStatus.SUCCESS){
            val jsonData=GetJsonData(this)
            jsonData.execute(data)
        }
    }

        override fun onDataAvailable(data: List<Film>) {
        movieListAdapter.loadNewData(data)
    }

    override fun onItemClick(view: View, position: Int) {
        val movie=movieListAdapter.getMovie(position)
        if(movie!=null){
            val intent=Intent(this,MovieDetails::class.java)
            intent.putExtra("Movie",movie)
            startActivity(intent)
        }
    }


}
