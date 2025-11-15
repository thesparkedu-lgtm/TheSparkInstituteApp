package com.example.thesparkinstituteapp.Video_Classes

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.thesparkinstituteapp.R

class VideoAdapter(
    private val videoList: List<VideoModel>,
    private val onVideoClick: (VideoModel) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.videoTitle)
        val description = itemView.findViewById<TextView>(R.id.videoDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]
        holder.title.text = video.videoTitle
        holder.description.text = video.videoInfo

        holder.itemView.setOnClickListener {
            onVideoClick(video)
        }
    }

    override fun getItemCount(): Int = videoList.size
}


