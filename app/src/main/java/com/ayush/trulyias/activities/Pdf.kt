package com.ayush.trulyias.activities

import android.annotation.SuppressLint
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ayush.trulyias.R
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class Pdf : AppCompatActivity() {

    private lateinit var pdfView: PDFView

//    private val pdfUrl = "https://firebasestorage.googleapis.com/v0/b/geeksforgeeks567.appspot.com/o/Complete%20DBMS%20Notes%20!%20CodewithCurious.com.pdf?alt=media&token=67d33134-4af7-4a05-adbd-e87dd3b36f41&_gl=1*40kz8i*_ga*NTQ0MDc1NjY1LjE2OTY0MTk5MTA.*_ga_CW55HF8NVT*MTY5NjU4NjQ3MC44LjEuMTY5NjU4NjY0Mi42MC4wLjA."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)

        pdfView = findViewById(R.id.pdfView)

        val pdfUrl = intent.getStringExtra("PDF_URL")

        RetrievePDFfromUrl().execute(pdfUrl)

        pdfView.fromUri(Uri.parse(pdfUrl))
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .onPageError { page, _ ->
                Toast.makeText(this,"Error Occured- {$page}",Toast.LENGTH_SHORT).show()
            }
            .onLoad { nbPages ->
                Toast.makeText(this,"Loading...",Toast.LENGTH_SHORT).show()
            }
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10)
            .load()
    }


    @SuppressLint("StaticFieldLeak")
    inner class RetrievePDFfromUrl : AsyncTask<String, Void, InputStream>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg strings: String): InputStream? {
            var inputStream: InputStream? = null
            try {
                val url = URL(strings[0])
                val urlConnection = url.openConnection() as HttpURLConnection
                if (urlConnection.responseCode == 200) {
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            return inputStream
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(inputStream: InputStream?) {
            if (inputStream != null) {
                pdfView.fromStream(inputStream).load()
            }
        }
    }
}
