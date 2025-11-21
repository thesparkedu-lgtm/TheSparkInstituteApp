package com.example.thesparkinstituteapp.notes_and_Pdf_Reader

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.animation.AlphaAnimation
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thesparkinstituteapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Pdf_reader : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var downloadBtn: FloatingActionButton
    private lateinit var progressBar: ProgressBar
    private var pdfUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)

        webView = findViewById(R.id.pdfWebView)
        downloadBtn = findViewById(R.id.downloadBtn)
        progressBar = findViewById(R.id.progressBar)

        pdfUrl = intent.getStringExtra("pdf_url")
        if (pdfUrl.isNullOrEmpty()) {
            Toast.makeText(this, "No PDF URL provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Enable zoom and smooth experience
        webView.settings.apply {
            javaScriptEnabled = true
            builtInZoomControls = true
            displayZoomControls = false
            loadWithOverviewMode = true
            useWideViewPort = true
        }

        // Hide scrollbars and improve loading look
        webView.isHorizontalScrollBarEnabled = false
        webView.isVerticalScrollBarEnabled = false

        // Load PDF in Google Docs viewer (no page count)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
                webView.startAnimation(fadeIn)
                webView.visibility = View.VISIBLE
            }
        }

        val googleDocsUrl = "https://docs.google.com/gview?embedded=true&url=$pdfUrl"
        webView.loadUrl(googleDocsUrl)

        // Handle downloads
        downloadBtn.setOnClickListener { downloadPdf(pdfUrl!!) }
    }

    private fun downloadPdf(url: String) {
        try {
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("Downloading PDF")
                .setDescription("Your file is being saved...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    url.substringAfterLast("/")
                )

            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Download failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}