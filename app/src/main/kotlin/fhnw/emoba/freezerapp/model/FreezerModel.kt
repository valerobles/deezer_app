package fhnw.emoba.freezerapp.model

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import fhnw.emoba.freezerapp.data.DeezerService
import fhnw.emoba.freezerapp.data.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FreezerModel(val deezerService: DeezerService) {

    val title = "Deezer App"


    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var currentTab by mutableStateOf(Tab.HITSTAB)
    var currentScreen by mutableStateOf(Screen.HOMESCREEN)
    var currentSong: Song? by mutableStateOf(null)
    var listOfSongs: List<Song> by mutableStateOf(emptyList())
    var searchText by mutableStateOf("")
    var isPlaying by mutableStateOf(false)
    var playerMode by mutableStateOf(false)


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
            listOfSongs = deezerService.requestSong(searchText).toMutableStateList() //favoriteCities.map { weatherService.requestForecast(it) }
            isLoading = false

        }
    }

    fun startUp(){
        var list = mutableListOf(Song(138545995,
            "Hello",
            "https://api.deezer.com/album/14880539/image",
            "25",
            "Adele",
            "https://e-cdns-images.dzcdn.net/images/artist/e5fc443d2abc03b487234ba4de65a001/56x56-000000-80-0-0.jpg",
            "https://e-cdns-images.dzcdn.net/images/artist/e5fc443d2abc03b487234ba4de65a001/250x250-000000-80-0-0.jpg",
            false,
            "https://cdns-preview-c.dzcdn.net/stream/c-cf968741c42b47400aca81b6da437a03-3.mp3"))
        list.addAll(0, arrayListOf(Song(138545995,
            "Hello",
            "https://api.deezer.com/album/14880539/image",
            "25",
            "Adele",
            "https://e-cdns-images.dzcdn.net/images/artist/e5fc443d2abc03b487234ba4de65a001/56x56-000000-80-0-0.jpg",
            "https://e-cdns-images.dzcdn.net/images/artist/e5fc443d2abc03b487234ba4de65a001/250x250-000000-80-0-0.jpg",
            false,
            "https://cdns-preview-c.dzcdn.net/stream/c-cf968741c42b47400aca81b6da437a03-3.mp3"),
            Song(138545995,
                "Hello",
                "https://api.deezer.com/album/14880539/image",
                "25",
                "Adele",
                "https://e-cdns-images.dzcdn.net/images/artist/e5fc443d2abc03b487234ba4de65a001/56x56-000000-80-0-0.jpg",
                "https://e-cdns-images.dzcdn.net/images/artist/e5fc443d2abc03b487234ba4de65a001/250x250-000000-80-0-0.jpg",
                false,
                "https://cdns-preview-c.dzcdn.net/stream/c-cf968741c42b47400aca81b6da437a03-3.mp3"),
            Song(138545995,
                "Hello",
                "https://api.deezer.com/album/14880539/image",
                "25",
                "Adele",
                "https://e-cdns-images.dzcdn.net/images/artist/e5fc443d2abc03b487234ba4de65a001/56x56-000000-80-0-0.jpg",
                "https://e-cdns-images.dzcdn.net/images/artist/e5fc443d2abc03b487234ba4de65a001/250x250-000000-80-0-0.jpg",
                false,
                "https://cdns-preview-c.dzcdn.net/stream/c-cf968741c42b47400aca81b6da437a03-3.mp3"),
            Song(138545995,
                "Hello",
                "https://api.deezer.com/album/14880539/image",
                "25",
                "Adele",
                "https://e-cdns-images.dzcdn.net/images/artist/e5fc443d2abc03b487234ba4de65a001/56x56-000000-80-0-0.jpg",
                "https://e-cdns-images.dzcdn.net/images/artist/e5fc443d2abc03b487234ba4de65a001/250x250-000000-80-0-0.jpg",
                false,
                "https://cdns-preview-c.dzcdn.net/stream/c-cf968741c42b47400aca81b6da437a03-3.mp3")))

        list.forEach {it.loadImage() }
        listOfSongs = list
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

    }





