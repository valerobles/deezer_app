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

data class Song(val id: Int,
                val title: String,
                val album_cover: String = "",
                val album_title: String = "",
                val artist: String,
                val artist_picture_small: String,
                val artist_picture_medium: String,
                var liked : Boolean = false,
                var songPreview : String) {

    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    var albumImage by mutableStateOf(DEFAULT_ICON.asImageBitmap())
    var artistImage by mutableStateOf(DEFAULT_ICON.asImageBitmap())

    fun loadImage(){
        modelScope.launch {
            albumImage = requestImage(album_cover)
            artistImage = requestImage(artist_picture_medium)
        }
    }


    constructor(json: JSONObject, liked: Boolean = false) : this (
        id = json.getInt("id"),
        title = json.getString("title"),
        album_cover = json.getJSONObject("album").getString("cover_medium"),
        album_title = json.getJSONObject("album").getString("title"),
        artist = json.getJSONObject("artist").getString("name"),
        artist_picture_small = json.getJSONObject("artist").getString("picture_small"),
        artist_picture_medium = json.getJSONObject("artist").getString("picture_medium"),
        songPreview = json.getString("preview"),
        liked = liked
    )


    fun asJson(): String {
        return """
            {
            "id":       $id,
            "title":    $title
            "album":    {
                        "cover_medium": $album_cover
                        "title":        $album_title
                        }
            "artist":   {
                        "name":             $artist
                        "picture_small":    $artist_picture_small
                        "picture_medium":   $artist_picture_medium
                        }
            "preview":  $songPreview

            }
    """.trimIndent()
    }
}