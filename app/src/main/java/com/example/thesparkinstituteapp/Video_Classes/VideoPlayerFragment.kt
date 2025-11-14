package com.example.thesparkinstituteapp.Video_Classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoPlayerFragment : Fragment() {

    private var videoId: String? = null
    private var videoInfo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoId = arguments?.getString("videoId")
        videoInfo = arguments?.getString("videoInfo")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_video_player, container, false)

        val youTubePlayerView =
            view.findViewById<com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView>(
                R.id.youtubePlayerView
            )

        val videoInfoText = view.findViewById<TextView>(R.id.videoInfoText)
        videoInfoText.text = videoInfo ?: "No description available"

        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object :
            com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener() {
            override fun onReady(player: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer) {
                videoId?.let { player.loadVideo(it, 0f) }
            }
        })

        return view
    }
}

