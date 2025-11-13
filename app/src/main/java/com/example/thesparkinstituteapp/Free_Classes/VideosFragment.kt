package com.example.thesparkinstituteapp.Free_Classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesparkinstituteapp.Courses_Classes.Courses_Classes_Activity
import com.example.thesparkinstituteapp.R


class VideosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_videos, container, false)

        val toolbar = view.findViewById<Toolbar>(R.id.videosToolbar)

        val recyclerView = view.findViewById<RecyclerView>(R.id.videosRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val adapter = VideosAdapter(VideoRepository.myVideos) { video ->
            (activity as? Courses_Classes_Activity)?.openPlayerFragment(video.videoId)
        }
        recyclerView.adapter = adapter



        return view
    }


}