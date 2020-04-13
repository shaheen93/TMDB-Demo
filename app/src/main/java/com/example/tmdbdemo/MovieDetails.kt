package com.example.tmdbdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_details.*

class MovieDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)

        val movie=intent.getSerializableExtra("Film") as Film
        detail_title.text=movie.title
        detail_overview.text=movie.overview
        detail_release_date.text=movie.release_date
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${movie.poster_path}").error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(detail_poster)

    }
}
