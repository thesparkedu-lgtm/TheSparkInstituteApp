package com.example.thesparkinstituteapp.Free_Classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thesparkinstituteapp.R

class VideosAdapter(
    private val videos: List<Video>,
    private val onItemClick: (Video) -> Unit
) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnailImageView)
        val title: TextView = itemView.findViewById(R.id.titleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.title.text = video.title

        // Load thumbnail using Glide
        Glide.with(holder.itemView.context)
            .load("https://img.youtube.com/vi/${video.videoId}/0.jpg")
            .into(holder.thumbnail)

        holder.itemView.setOnClickListener { onItemClick(video) }
    }

    override fun getItemCount(): Int = videos.size
}
