package com.example.thesparkinstituteapp

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.animation.AlphaAnimation
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Pdf_reader : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var downloadBtn: Button
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
            Toast.makeText(this, "No PDF URL", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // WebView settings
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                // Hide progress bar
                progressBar.visibility = View.GONE

                // Fade-in animation
                val fadeIn = AlphaAnimation(0f, 1f)
                fadeIn.duration = 500
                webView.startAnimation(fadeIn)
                webView.visibility = View.VISIBLE
            }
        }

        // Load PDF via Google Docs
        val googleDocsUrl = "https://docs.google.com/gview?embedded=true&url=$pdfUrl"
        webView.loadUrl(googleDocsUrl)

        // Download button
        downloadBtn.setOnClickListener { downloadPdf(pdfUrl!!) }
    }

    private fun downloadPdf(url: String) {
        try {
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("Downloading PDF")
                .setDescription("Downloading note")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    url.substringAfterLast("/")
                )

            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)

            Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Download failed", Toast.LENGTH_SHORT).show()
        }
    }
}
