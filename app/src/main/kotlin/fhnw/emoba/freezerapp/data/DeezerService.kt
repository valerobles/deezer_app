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

    //fun searchAlbum(searchText: String) : List<Album>

    //fun searchRadio() : List<Radio>
}