package com.example.thesparkinstituteapp.Video_Classes

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoPlayerFragment : Fragment() {

    private var videoId: String? = null
    private var videoDescription: String? = null  // FULL DESCRIPTION
    private var isFullScreen = false

    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var videoInfoText: TextView
    private lateinit var fullscreenBtn: ImageButton
    private lateinit var descriptionScroll: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videoId = arguments?.getString("videoId")
        videoDescription = arguments?.getString("videoDescription")

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isFullScreen) {
                        exitFullScreen()
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_video_player, container, false)

        youTubePlayerView = view.findViewById(R.id.youtubePlayerView)
        videoInfoText = view.findViewById(R.id.videoDescription)
        fullscreenBtn = view.findViewById(R.id.fullscreenBtn)
        descriptionScroll = view.findViewById(R.id.scrollView)

        // FULL description shown here
        videoInfoText.text = videoDescription ?: "No description available"

        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                videoId?.let { player.loadVideo(it, 0f) }
            }
        })

        fullscreenBtn.setOnClickListener { toggleFullScreen() }

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        return view
    }

    private fun toggleFullScreen() {
        val params =
            youTubePlayerView.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams

        if (!isFullScreen) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            descriptionScroll.visibility = View.GONE

            params.dimensionRatio = null
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            youTubePlayerView.layoutParams = params

            isFullScreen = true
        } else {
            exitFullScreen()
        }
    }

    private fun exitFullScreen() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        descriptionScroll.visibility = View.VISIBLE

        val params =
            youTubePlayerView.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
        params.width = 0
        params.height = 0
        params.dimensionRatio = "16:9"
        youTubePlayerView.layoutParams = params

        isFullScreen = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        youTubePlayerView.release()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
}
