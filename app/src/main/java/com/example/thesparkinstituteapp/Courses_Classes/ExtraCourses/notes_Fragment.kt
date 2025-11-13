package com.example.thesparkinstituteapp.Notes

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.thesparkinstituteapp.Note
import com.example.thesparkinstituteapp.NotesAdapter
import com.example.thesparkinstituteapp.Pdf_reader
import com.example.thesparkinstituteapp.R
import com.google.firebase.database.*

class notes_Fragment : Fragment() {

    private lateinit var database: DatabaseReference
    private val allNotes = mutableListOf<Note>()
    private val filteredNotes = mutableListOf<Note>()
    private lateinit var adapter: NotesAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.notesRecyclerView)
        val categoryButton: Button = view.findViewById(R.id.categoryButton)
        progressBar = view.findViewById(R.id.progressBar)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        adapter = NotesAdapter(filteredNotes) { note ->
            val intent = Intent(requireContext(), Pdf_reader::class.java)
            intent.putExtra("pdf_url", note.url)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("notes")
        loadAllNotes()

        // 🔄 Slide to refresh
        swipeRefreshLayout.setOnRefreshListener {
            loadAllNotes()
        }

        categoryButton.setOnClickListener {
            showCategoryMenu(it as Button)
        }

        return view
    }

    private fun showCategoryMenu(button: Button) {
        val popup = PopupMenu(requireContext(), button)
        popup.menuInflater.inflate(R.menu.category_menu, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.navodaya -> filterNotesBy("Navodaya")
                R.id.computer -> filterNotesBy("Computer")
                R.id.coaching -> filterNotesBy("Coaching")
                R.id.all -> showAllNotes()
            }
            true
        }
        popup.show()
    }

    private fun loadAllNotes() {
        progressBar.visibility = View.VISIBLE

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                allNotes.clear()
                filteredNotes.clear()

                for (child in snapshot.children) {
                    val title = child.child("title").getValue(String::class.java) ?: continue
                    val description = child.child("description").getValue(String::class.java) ?: ""
                    val url = child.child("pdfUrl").getValue(String::class.java) ?: continue

                    // Add normally
                    allNotes.add(Note(title, description, url))
                }

                // 🔝 Sort by latest first (newest at top)
                allNotes.reverse()

                filteredNotes.addAll(allNotes)
                adapter.notifyDataSetChanged()

                progressBar.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false

                if (allNotes.isEmpty()) {
                    Toast.makeText(requireContext(), "No notes found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.visibility = View.GONE
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterNotesBy(category: String) {
        val filtered = allNotes.filter {
            it.description?.contains(category, ignoreCase = true) == true
        }

        filteredNotes.clear()
        filteredNotes.addAll(filtered)
        adapter.notifyDataSetChanged()

        if (filteredNotes.isEmpty()) {
            Toast.makeText(requireContext(), "No notes found for $category", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAllNotes() {
        filteredNotes.clear()
        filteredNotes.addAll(allNotes)
        adapter.notifyDataSetChanged()
    }
}
