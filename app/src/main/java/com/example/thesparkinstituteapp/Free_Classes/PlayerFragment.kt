package com.example.thesparkinstituteapp.Free_Classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.example.thesparkinstituteapp.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class PlayerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      val view =  inflater.inflate(R.layout.fragment_player, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.playerToolbar)
        val playerView = view.findViewById<YouTubePlayerView>(R.id.youtubePlayerView)

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        lifecycle.addObserver(playerView)

        val videoId = arguments?.getString("videoId") ?: ""

        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                player.loadVideo(videoId, 0f)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        view?.findViewById<YouTubePlayerView>(R.id.youtubePlayerView)?.release()
    }


}