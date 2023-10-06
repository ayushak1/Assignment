package com.ayush.trulyias

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoViews : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var btnPlayPau: Button
    private lateinit var seekBar: SeekBar
    private lateinit var btnHandout: Button

    private val videoUrl = "https://media.geeksforgeeks.org/wp-content/uploads/20230920195732/gmailandroid.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_views)

        videoView = findViewById(R.id.videoView)
        btnPlayPau = findViewById(R.id.btnPlayPau)
        seekBar = findViewById(R.id.seekBar)
        btnHandout = findViewById(R.id.btnHandout)

        videoView.setVideoPath(videoUrl)
        videoView.setOnPreparedListener { mediaPlayer ->
            seekBar.max = mediaPlayer.duration
        }

        btnPlayPau.setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
                btnPlayPau.text = "Play"
            } else {
                videoView.start()
                btnPlayPau.text = "Pause"
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
            val intent = Intent(this, Pdf::class.java)
            startActivity(intent)
        }

    }
}
