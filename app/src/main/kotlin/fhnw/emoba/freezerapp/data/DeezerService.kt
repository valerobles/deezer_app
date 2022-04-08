package fhnw.emoba.freezerapp.data

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap


val DEFAULT_ICON: Bitmap = Bitmap.createBitmap(
    120,
    120,
    Bitmap.Config.ALPHA_8
)

interface DeezerService {

    fun requestSong(searchText: String) : List<Song>

    fun requestImage(url: String): ImageBitmap

    fun requestAlbum(searchText: String) : List<Album>

    fun requestAlbumSongs(album: Album,tracklist: String): List<Song>

    fun requestRatio(): List<Radio>

    fun requestRadioSongs(radio: Radio, tracklist: String): List<Song>
}