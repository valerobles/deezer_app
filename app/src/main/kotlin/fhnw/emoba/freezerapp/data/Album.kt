package fhnw.emoba.freezerapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.ui.screens.requestImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.json.JSONObject


data class Album(val id: Int,
                val title: String,
                val cover: String,
                val cover_medium: String,
                val artist: String,
                var tracklist : String) {

    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    var albumImage by mutableStateOf(DEFAULT_ICON.asImageBitmap())

    fun loadImage(){
        modelScope.launch {
            albumImage = requestImage(cover_medium)
        }
    }



    constructor(json: JSONObject) : this (
        id = json.getInt("id"),
        title = json.getString("title"),
        cover = json.getString("cover"),
        cover_medium = json.getString("cover_medium"),
        artist = json.getJSONObject("artist").getString("name"),
        tracklist = json.getString("tracklist")
    )
}