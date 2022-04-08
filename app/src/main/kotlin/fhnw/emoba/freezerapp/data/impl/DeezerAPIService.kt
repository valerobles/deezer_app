package fhnw.emoba.freezerapp.data.impl

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.data.*
import org.json.JSONObject

class DeezerAPIService : DeezerService{


    override fun requestSong(searchText: String): List<Song> {
        val baseURL = "https://api.deezer.com/search?q="
        val listOfSongs: MutableList<Song> = mutableListOf()
        val json = JSONObject(content(baseURL + searchText))
        val data = json.getJSONArray("data")

        for (i in 0 until data.length()) {
            listOfSongs.add(Song(data.getJSONObject(i)))
            listOfSongs.last().loadImage()
        }
        return listOfSongs

    }

    override fun requestImage(url: String): ImageBitmap {
        return try {
            bitmap(url).asImageBitmap()
        } catch (e: Exception) {
            DEFAULT_ICON.asImageBitmap()
        }
    }


}