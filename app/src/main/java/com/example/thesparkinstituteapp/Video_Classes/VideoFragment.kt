package com.example.thesparkinstituteapp.Video_Classes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thesparkinstituteapp.R
import com.google.firebase.database.*

class VideoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var spinner: Spinner
    private lateinit var adapter: VideoAdapter

    private val videoList = ArrayList<VideoModel>()       // All videos from Firebase
    private val filteredList = ArrayList<VideoModel>()    // Videos filtered by category

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_video, container, false)

        recyclerView = view.findViewById(R.id.videoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        spinner = view.findViewById(R.id.categorySpinner)
        val categories = arrayOf("All", "Navodaya", "Computer", "Competitive", "Coaching")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        adapter = VideoAdapter(filteredList) { video ->
            openVideoPlayer(video.videoId, video.videoInfo)
        }
        recyclerView.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = categories[position]
                filterVideos(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        fetchVideos()

        return view
    }

    private fun fetchVideos() {
        val database = FirebaseDatabase.getInstance().getReference("videos")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                videoList.clear()
                for (videoSnap in snapshot.children) {
                    val video = videoSnap.getValue(VideoModel::class.java)
                    if (video != null) videoList.add(video)
                }
                filterVideos(spinner.selectedItem.toString()) // Apply current category filter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("VideoFragment", "Firebase error: ${error.message}")
            }
        })
    }

    private fun filterVideos(category: String) {
        filteredList.clear()
        if (category == "All") {
            filteredList.addAll(videoList)
        } else {
            filteredList.addAll(videoList.filter { it.category == category })
        }
        adapter.notifyDataSetChanged()
    }

    private fun openVideoPlayer(videoId: String, videoInfo: String) {
        val fragment = VideoPlayerFragment()
        val bundle = Bundle()
        bundle.putString("videoId", videoId)
        bundle.putString("videoInfo", videoInfo)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_2, fragment)
            .addToBackStack(null)
            .commit()
    }
}
