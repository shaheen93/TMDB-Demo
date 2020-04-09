package com.example.tmdbdemo

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.net.URL

class GetJsonData(private  val listener:OnDataAvailable):AsyncTask<String,Void,List<Movie>>() {

    interface OnDataAvailable {
        fun onDataAvailable(data: List<Movie>)
    }

    override fun onPostExecute(result: List<Movie>) {
        super.onPostExecute(result)
        listener.onDataAvailable(result)
    }

    override fun doInBackground(vararg params: String): ArrayList<Movie> {

        val movieList = ArrayList<Movie>()
        try{
            val jsonData=JSONObject(params[0])
            val itemsArray=jsonData.getJSONArray("results")


            for (i in 0 until itemsArray.length()){
                val jsonMovie=itemsArray.getJSONObject(i)
                val title= jsonMovie.getString("title")
                val overview= jsonMovie.getString("overview")
                val releaseDate= jsonMovie.getString("release_date")
                val imgUrl= jsonMovie.getString("poster_path")
                val movie=Movie(title,overview,releaseDate,imgUrl)
                movieList.add(movie)
            }
        }catch (e: Exception){
            Log.e("Error",e.message)
        }
        Log.d("Compiling","finished")

        return movieList
    }
}