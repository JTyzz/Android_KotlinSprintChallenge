package com.earthdefensesystem.android_kotlinsprintchallenge

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.VideoView
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import java.lang.Runnable

class MainActivity : AppCompatActivity() {

    val VIDEO_URL = "http://hubblesite.org/api/v3/video/200"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val videoView = findViewById<VideoView>(R.id.video_view)
        val playButton = findViewById<Button>(R.id.video_button)
        val seekbar = findViewById<SeekBar>(R.id.seek_bar)

        val dataJob = Job()
        val dataScope = CoroutineScope(Dispatchers.IO + dataJob)

        dataScope.launch {
            val (success, result) = NetworkAdapter.httpGetRequest(VIDEO_URL)
            if (success) {
                val videoData = Json.nonstrict.parse(VideoData.serializer(), result)
                val videoUrl = Uri.parse(videoData.getVideoUrl())
                seekbar.max = videoView.duration
                withContext(Dispatchers.Main) {
                    videoView.setVideoURI(videoUrl)
                    videoView.start()
                }
            }

            seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        videoView.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }


        playButton.text = "Stop"
        playButton.setOnClickListener {
            val isPlaying = videoView.isPlaying
            playButton.text = if (isPlaying) "Play" else "Stop"
            if (isPlaying) {
                videoView.pause()
            } else {
                videoView.start()
            }
        }
    }
}
