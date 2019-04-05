package com.earthdefensesystem.android_kotlinsprintchallenge

import android.support.annotation.WorkerThread
import kotlinx.serialization.json.Json


object SpaceDao {
    const val VIDEO_URL = "http://hubblesite.org/api/v3/video/1199"

    @WorkerThread
    suspend fun getVideo(): Video? {
        val (success, result) = NetworkAdapter.httpGetRequest(VIDEO_URL)
        var video: Video? = null
        if (success) {
            video = Json.parse(Video.serializer(), result)
        }
        return video
    }
}