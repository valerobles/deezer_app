package fhnw.emoba.freezerapp.model

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.*
import fhnw.emoba.freezerapp.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FreezerModel(val deezerService: DeezerService) {

    val title = "Deezer App"


    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var currentTab by mutableStateOf(Tab.SONGSTAB)
    var currentScreen by mutableStateOf(Screen.HOMESCREEN)
    var currentSong: Song? by mutableStateOf(null)

    var nextSong: Song? by mutableStateOf(null)
    var lastSong: Song? by mutableStateOf(null)

    var listOfSongs: List<Song> by mutableStateOf(emptyList())
    var searchText by mutableStateOf("")
    var isPlaying by mutableStateOf(false)
    var playerMode by mutableStateOf(false)

    var listOfAlbums: List<Album> by mutableStateOf(emptyList())
    var currentAlbum: Album? by mutableStateOf(null)
    var listOfAlbumSongs: List<Song> by mutableStateOf(emptyList())

    var listOfRadio: List<Radio> by mutableStateOf(emptyList())
    var listOfRadioSongs: List<Song> by mutableStateOf(emptyList())
    var currentRadio: Radio? by mutableStateOf(null)

    var playerBarOn by mutableStateOf(false)

    var currentPlaylist: List<Song> by mutableStateOf(emptyList())

    var listOfArtists: MutableList<Artist> = mutableStateListOf()
    var listOfArtistsSongs: List<Song> by mutableStateOf(emptyList())
    var currentArtist: Artist? by mutableStateOf(null)


    var favoriteSongs: MutableList<Song> = mutableStateListOf()




    var isLoading by mutableStateOf(false)


    private val player = MediaPlayer().apply {
        setOnCompletionListener { this@FreezerModel.isPlaying = true }
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        setOnPreparedListener {
            start()
        }
    }


    fun fetchSongs() {
        isLoading = true
        listOfSongs = emptyList()
        modelScope.launch {
            listOfSongs = deezerService.requestSong(searchText).toMutableStateList()
            isLoading = false

        }
    }

    fun fetchAlbums(){
        isLoading = true
        listOfAlbums = emptyList()
        modelScope.launch {
            listOfAlbums = deezerService.requestAlbum(searchText).toMutableStateList()
            print(listOfAlbumSongs)
            isLoading = false
        }
    }

    fun fetchAlbumSongs(){
        isLoading = true
        listOfAlbumSongs = emptyList()
        modelScope.launch {
            listOfAlbumSongs = currentAlbum?.let { deezerService.requestAlbumSongs(it,it.tracklist) }!!
            isLoading = false
        }


    }

    fun fetchRadioStation(){
        isLoading = true
        listOfRadio = emptyList()
        modelScope.launch {
            listOfRadio = deezerService.requestRatio().subList(0,6)
            isLoading = false
        }
    }

    fun fetchRadioSongs(){
        isLoading = true
        listOfRadioSongs = emptyList()
        modelScope.launch {
            listOfRadioSongs = currentRadio?.let { deezerService.requestRadioSongs(it, currentRadio!!.tracklist) }!!
            isLoading = false
        }


    }


    fun fetchArtistSongs(){
        isLoading = true
        listOfArtistsSongs = emptyList()
        modelScope.launch {
            listOfArtistsSongs = currentArtist?.let { deezerService.requestArtistSongs(it,
                currentArtist!!.tracklist) }!!
            isLoading = false
        }
    }

    fun fetchArtist(artistID: Int){
        isLoading = true
        modelScope.launch {
            listOfArtists =
                deezerService.requestArtist(artistID,listOfArtists) as MutableList<Artist> // TODO only add one
            isLoading = false
        }
    }

    fun startUp(){
        val list = mutableListOf(27,246791,160,564)
        list.forEach { fetchArtist(it) }

        fetchRadioStation()

    }



    fun startStopPlayer(song: Song){

        if (isPlaying)
            pausePlayer()
        else
            startPlayer(song = song)

    }

    private fun startPlayer(song : Song) {
        player.reset()
        player.setDataSource(song.songPreview)
        player.prepareAsync()
        player.start()
        isPlaying = true

            }

    private fun pausePlayer(){
        player.pause()
        isPlaying = false
    }



    fun playNextSong() {
        val nextSongIndex = currentPlaylist.indexOf(currentSong) + 1
        if (nextSongIndex >= currentPlaylist.size) {
            currentSong = currentPlaylist.first()
            startPlayer(currentSong!!)
        }
        else {
            currentSong = currentPlaylist[nextSongIndex]
            startPlayer(currentSong!!)
        }
    }

    fun playPreviousSong() {
        val previousSongIndex = currentPlaylist.indexOf(currentSong) - 1
        if (previousSongIndex < 0) {
            currentSong = currentPlaylist.last()
            startPlayer(currentSong!!)
        }
        else {
            currentSong = currentPlaylist[previousSongIndex]
            startPlayer(currentSong!!)
        }
    }

    fun addRemoveFavorite(song : Song) {
        if (song.liked) {
            favoriteSongs.remove(song)
            song.liked = false
        }
        else {
            favoriteSongs.add(song)
            song.liked = true
        }
    }




    }





