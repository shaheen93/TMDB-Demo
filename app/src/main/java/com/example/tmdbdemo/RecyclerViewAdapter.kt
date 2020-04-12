package com.example.tmdbdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ViewHolder(v: View):RecyclerView.ViewHolder(v){

    var poster:ImageView=v.findViewById(R.id.list_poster)
    var title:TextView=v.findViewById(R.id.list_title)
    var releaseDate:TextView=v.findViewById(R.id.list_release_date)


}

class RecyclerViewAdapter(private var movieList:List<Film>) : RecyclerView.Adapter<ViewHolder>() {
    private var count=0;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.movie_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        count = movieList.size
        return count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tempMovie=movieList[position]
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${tempMovie.poster_path}").error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(holder.poster)
        holder.title.text="Movie Title:\n${tempMovie.title}"
        holder.releaseDate.text="Release Date:\n${tempMovie.release_date}"
    }

    fun loadNewData(newMovies:List<Film>){
        movieList+=newMovies

        notifyDataSetChanged()
    }
    fun getMovie(position:Int):Film?{
        return if(movieList.isNotEmpty()) movieList[position] else null
    }
}