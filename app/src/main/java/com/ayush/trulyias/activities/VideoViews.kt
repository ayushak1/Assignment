package com.ayush.trulyias.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.ayush.trulyias.R

class VideoViews : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var btnPlayPause: Button
    private lateinit var seekBar: SeekBar
    private lateinit var btnHandout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_views)

        videoView = findViewById(R.id.videoView)
        btnPlayPause = findViewById(R.id.btnPlayPau)
        seekBar = findViewById(R.id.seekBar)
        btnHandout = findViewById(R.id.btnHandout)

        val videoResourceId = intent.getIntExtra("VIDEO_RESOURCE_ID", 0)
        val pdfUrl = intent.getStringExtra("PDF_URL")

        val videoPath = "android.resource://" + packageName + "/" + videoResourceId

        videoView.setVideoPath(videoPath)
        videoView.setOnPreparedListener { mediaPlayer ->
            seekBar.max = mediaPlayer.duration
        }

        btnPlayPause.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                btnPlayPause.text = "Play"
            } else {
                videoView.start()
                btnPlayPause.text = "Pause"
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    videoView.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnHandout.setOnClickListener {
            val intent = Intent(this@VideoViews, Pdf::class.java)
            intent.putExtra("PDF_URL",pdfUrl)
            startActivity(intent)
        }
    }
}
