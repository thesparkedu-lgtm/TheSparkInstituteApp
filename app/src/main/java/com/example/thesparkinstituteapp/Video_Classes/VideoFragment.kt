package com.example.thesparkinstituteapp.Video_Classes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesparkinstituteapp.R
import com.google.firebase.database.*

class VideoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val videoList = ArrayList<VideoModel>()
    private lateinit var adapter: VideoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val view = inflater.inflate(R.layout.fragment_video, container, false)
        recyclerView = view.findViewById(R.id.videoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = VideoAdapter(videoList) { video ->
            openVideoPlayer(video.videoId)
        }
        recyclerView.adapter = adapter

        fetchVideos()
        return view
    }

    private fun fetchVideos() {
        val database = FirebaseDatabase.getInstance()
            .getReference("videos")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                videoList.clear()
                for (videoSnap in snapshot.children) {
                    val video = videoSnap.getValue(VideoModel::class.java)
                    if (video != null) videoList.add(video)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("VideoList", "Error: ${error.message}")
            }
        })
    }

    private fun openVideoPlayer(videoId: String) {
        val fragment = VideoPlayerFragment()
        val bundle = Bundle()
        bundle.putString("videoId", videoId)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_2, fragment)
            .addToBackStack(null)
            .commit()
    }
}
