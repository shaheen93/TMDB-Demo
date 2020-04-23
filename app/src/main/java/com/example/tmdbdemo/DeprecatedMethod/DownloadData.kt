package com.example.tmdbdemo.DeprecatedMethod

import java.net.URL
import android.os.AsyncTask
import android.util.Log

enum class DownloadStatus{
    SUCCESS,FAIL,IDLE
}
class DownloadData(private val listener: OnDownloadComplete) : AsyncTask<String,Void,String>() {

    private var status= DownloadStatus.IDLE

    interface OnDownloadComplete {
        fun onDownloadComplete(data: String,status: DownloadStatus)
    }


    override fun onPostExecute(result: String) {
        Log.d("Result:",result)
        listener.onDownloadComplete(result,status)
       }

    override fun doInBackground(vararg params: String?): String {

        if(params[0]==null){
            status= DownloadStatus.FAIL
            return "No URL provided"
        }
        try{
            status= DownloadStatus.SUCCESS
            return URL(params[0]).readText()
        }catch(e: Exception){
            status= DownloadStatus.FAIL
            Log.d("Download Error",e.message)
            return "Invalid URL ${e.message}"
        }
    }
}