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
    private var videoInfo: String? = null
    private var isFullScreen = false

    private lateinit var youTubePlayerView: YouTubePlayerView
    private lateinit var videoInfoText: TextView
    private lateinit var fullscreenBtn: ImageButton
    private lateinit var descriptionScroll: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videoId = arguments?.getString("videoId")
        videoInfo = arguments?.getString("videoInfo")

        // Handle back press
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isFullScreen) {
                        // Exit fullscreen first
                        exitFullScreen()
                    } else {
                        // Let the system handle back (pop fragment)
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

        // Initialize views
        youTubePlayerView = view.findViewById(R.id.youtubePlayerView)
        videoInfoText = view.findViewById(R.id.videoInfoText)
        fullscreenBtn = view.findViewById(R.id.fullscreenBtn)
        descriptionScroll = view.findViewById(R.id.scrollView)

        videoInfoText.text = videoInfo ?: "No description available"

        // Add YouTube player lifecycle
        lifecycle.addObserver(youTubePlayerView)

        // Load video
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                videoId?.let { player.loadVideo(it, 0f) }
            }
        })

        fullscreenBtn.setOnClickListener {
            toggleFullScreen()
        }

        // Lock orientation initially
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        return view
    }

    private fun toggleFullScreen() {
        val params = youTubePlayerView.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams

        if (!isFullScreen) {
            // Enter fullscreen
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            descriptionScroll.visibility = View.GONE

            // Remove dimension ratio to let player fill width and height
            params.dimensionRatio = null
            params.width = androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_PARENT
            params.height = androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.MATCH_PARENT
            youTubePlayerView.layoutParams = params

            isFullScreen = true
        } else {
            exitFullScreen()
        }
    }

    private fun exitFullScreen() {
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        descriptionScroll.visibility = View.VISIBLE

        // Restore 16:9 aspect ratio in portrait
        val params = youTubePlayerView.layoutParams as androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
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
