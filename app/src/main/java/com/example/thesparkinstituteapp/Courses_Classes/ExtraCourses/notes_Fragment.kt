package com.example.thesparkinstituteapp.Notes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.thesparkinstituteapp.Note
import com.example.thesparkinstituteapp.NotesAdapter
import com.example.thesparkinstituteapp.R
import com.google.firebase.database.*
import kotlin.jvm.java

class notes_Fragment : Fragment() {

    private lateinit var database: DatabaseReference
    private val notes = mutableListOf<Note>()
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.notesRecyclerView)
        val swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)

        adapter = NotesAdapter(notes) { note ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(note.url))
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("notes")

        progressBar.visibility = View.VISIBLE
        loadNotes(progressBar, swipeRefresh)

        swipeRefresh.setOnRefreshListener {
            loadNotes(progressBar, swipeRefresh)
        }

        return view
    }

    private fun loadNotes(progressBar: ProgressBar, swipeRefresh: SwipeRefreshLayout) {
        progressBar.visibility = View.VISIBLE
        Log.d("FirebaseDebug", "loadNotes() called")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("FirebaseDebug", "onDataChange triggered")
                notes.clear()  // Clear the list to avoid duplicates

                for (child in snapshot.children) {
                    val title = child.child("title").getValue(String::class.java)
                    val category =
                        child.child("description").getValue(String::class.java)  // map description
                    val url =
                        child.child("pdfUrl").getValue(String::class.java)           // map pdfUrl

                    if (title != null && category != null && url != null) {
                        // Prevent duplicate notes by URL
                        if (!notes.any { it.url == url }) {
                            notes.add(Note(title, category, url))
                        }
                    }
                }

                adapter.notifyDataSetChanged()  // Refresh RecyclerView
                progressBar.visibility = View.GONE
                swipeRefresh.isRefreshing = false

                if (notes.isEmpty()) {
                    Toast.makeText(requireContext(), "No notes found", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Loaded ${notes.size} notes",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.visibility = View.GONE
                swipeRefresh.isRefreshing = false
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_LONG)
                    .show()
            }
        })
    }
}