package fhnw.emoba.freezerapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.freezerapp.data.FreezerRepo
import fhnw.emoba.freezerapp.data.Song

class FreezerModel {
    val title = "Deezer App"

    lateinit var songs: List<Song>

    fun loadSongs(){
        songs = FreezerRepo.songs
    }

    //val songs: Songs
    var currentTab by mutableStateOf(Tab.HITSTAB)
    var currentScreen by mutableStateOf(Screen.HOMESCREEN)
    var currentSong: Song? by mutableStateOf(null) // problem!




}