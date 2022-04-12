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
    var currentPlaying: Song? by mutableStateOf(null) // Or current selected song?

    var selectedSong: Song? by mutableStateOf(null)


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



    var currentPlaylist: List<Song> by mutableStateOf(emptyList())

    var listOfArtists: MutableList<Artist> = mutableStateListOf()
    var listOfArtistsSongs: List<Song> by mutableStateOf(emptyList())
    var currentArtist: Artist? by mutableStateOf(null)


    var listOfFavoriteSongs: MutableList<Song> = mutableStateListOf()




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
            listOfRadio = deezerService.requestRadio().subList(0,6)
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
                deezerService.requestArtist(artistID,listOfArtists) as MutableList<Artist>
            isLoading = false
        }
    }

    // Have artists ready when starting application. Everytime you get new artists
    fun startUp(){
        val list = mutableListOf(27)
        for (i in 0 until 5){
            list.add((1..900).shuffled().first())
        }
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

       if (currentPlaying != song) {
           currentPlaying = song
           player.reset()
           player.setDataSource(currentPlaying!!.songPreview)
           player.prepareAsync()
           player.start()
           isPlaying = true
       } else
        player.start()
        isPlaying = true

    }

    private fun pausePlayer(){
        player.pause()
        isPlaying = false
    }



    fun playNextSong() {
        val nextSongIndex = currentPlaylist.indexOf(currentPlaying) + 1
        if (nextSongIndex >= currentPlaylist.size) {
            selectedSong = currentPlaylist.first()
            startPlayer(selectedSong!!)
        }
        else {
            selectedSong = currentPlaylist[nextSongIndex]
            startPlayer(selectedSong!!)
        }
    }

    fun playPreviousSong() {
        val previousSongIndex = currentPlaylist.indexOf(currentPlaying) - 1
        if (previousSongIndex < 0) {
            selectedSong = currentPlaylist.last()
            startPlayer(selectedSong!!)
        }
        else {
            selectedSong = currentPlaylist[previousSongIndex]
            startPlayer(selectedSong!!)
        }
    }

    fun replay() {
        player.reset()
        player.setDataSource(currentPlaying!!.songPreview)
        player.prepareAsync()
        player.start()
    }

    fun addRemoveFavorite(song : Song) {
        if (song.liked) {
            listOfFavoriteSongs.remove(song)
            song.liked = false
        }
        else {
            listOfFavoriteSongs.add(song)
            song.liked = true
        }
    }








    }





