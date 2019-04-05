package com.earthdefensesystem.android_kotlinsprintchallenge

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Video(
    val name: String,
    val short_description: String,
    val video_files: List<VideoFiles>,
    val youtube_id: String,
    @Optional
    val credits: String,
    val collection: String,
    val mission: String,
    val image: String,
    val image_retina: String
)
{fun getVideoUrl():String{
    return video_files.first().file_url
    }
}

@Serializable
data class VideoFiles(
    val file_url: String,
    val file_size: Int,
    val width: Int,
    val height: Int,
    val frame_rate: String,
    val format: String
)
