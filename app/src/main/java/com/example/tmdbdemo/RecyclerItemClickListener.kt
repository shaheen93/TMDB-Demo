package com.example.tmdbdemo

import android.content.Context
import android.net.sip.SipSession
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(context:Context,recyclerView:RecyclerView,private val listener:OnRecyclerClickListener):RecyclerView.SimpleOnItemTouchListener() {
    interface OnRecyclerClickListener{
        fun onItemClick(view:View,position:Int)
    }

    private val gestureDetector=GestureDetectorCompat(context,object :GestureDetector.SimpleOnGestureListener(){
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val childView=recyclerView.findChildViewUnder(e.x,e.y)
            if (childView != null) {
                listener.onItemClick(childView,recyclerView.getChildAdapterPosition(childView))
            }
            return true
        }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val result=gestureDetector.onTouchEvent(e)
        return super.onInterceptTouchEvent(rv, e)
    }
}