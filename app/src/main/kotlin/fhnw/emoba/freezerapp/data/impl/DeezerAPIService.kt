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

    override fun requestAlbum(searchText: String): List<Album> {

        val baseURL = "https://api.deezer.com/search/album?q="
        print(baseURL+searchText)
        val listOfAlbums: MutableList<Album> = mutableListOf()
        val json = JSONObject(content(baseURL + searchText))
        val data = json.getJSONArray("data")

        for (i in 0 until data.length()) {
            listOfAlbums.add(Album(data.getJSONObject(i)))
            listOfAlbums.last().loadImage()
        }
        return listOfAlbums

    }

    override fun requestAlbumSongs( album: Album,tracklist: String,): List<Song> {
        val listOfSongs: MutableList<Song> = mutableListOf()
        val json = JSONObject(content(tracklist))
        val data = json.getJSONArray("data")

        for (i in 0 until data.length()) {
            listOfSongs.add(Song(data.getJSONObject(i), album.title, album.cover_medium))
            listOfSongs.last().loadImage()
        }
        return listOfSongs.toList()
    }
    override fun requestRadio(): List<Radio> {
        val baseURL = "https://api.deezer.com/radio"
        val listOfRadios: MutableList<Radio> = mutableListOf()
        val json = JSONObject(content(baseURL))
        val data = json.getJSONArray("data")

        for (i in 0 until data.length()) {
            listOfRadios.add(Radio(data.getJSONObject(i)))
            listOfRadios.last().loadImage()
        }
        return listOfRadios.toList()
    }

    override fun requestRadioSongs( radio: Radio,tracklist: String): List<Song> {
        val listOfSongs: MutableList<Song> = mutableListOf()
        val json = JSONObject(content(tracklist))
        val data = json.getJSONArray("data")

        for (i in 0 until data.length()) {
            listOfSongs.add(Song(data.getJSONObject(i), radio.title, radio.picture_medium))
            listOfSongs.last().loadImage()
        }
        return listOfSongs.toList()
    }

    override fun requestArtist(artist: Int,listOfArtists: MutableList<Artist>): List<Artist>{
        val baseURL = "https://api.deezer.com/artist/"
        val json = JSONObject(content(baseURL+artist.toString()))

        listOfArtists.add(Artist(json))
        listOfArtists.last().loadImage()

        return listOfArtists.toList()
    }

    override fun requestArtistSongs( artist: Artist,tracklist: String): List<Song> {
        val listOfSongs: MutableList<Song> = mutableListOf()
        val json = JSONObject(content(tracklist))
        val data = json.getJSONArray("data")

        for (i in 0 until data.length()) {
            listOfSongs.add(Song(data.getJSONObject(i), artist.name, artist.picture_medium))
            listOfSongs.last().loadImage()
        }
        return listOfSongs.toList()
    }

}





