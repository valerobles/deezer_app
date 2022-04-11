package fhnw.emoba.freezerapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.freezerapp.data.impl.DeezerAPIService
import junit.framework.Assert.assertEquals
import org.json.JSONObject
import org.junit.Test


internal class DeezerServiceTest {
    var deezerService: DeezerService = DeezerAPIService()
    private val SongAsString = """
        {
        "id": "1109737",
     
      "title": "The Real Slim Shady",

      "preview": "https://cdns-preview-d.dzcdn.net/stream/c-d28ee67c24d60e740866c7709d772f55-12.mp3",
     
      "artist": {
         "name": "Eminem",
         "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/250x250-000000-80-0-0.jpg"
      },
      "album": {
         "title": "Curtain Call: The Hits",
        "cover_medium": "https://e-cdns-images.dzcdn.net/images/cover/e2b36a9fda865cb2e9ed1476b6291a7d/250x250-000000-80-0-0.jpg"
      }
    }
    
        """.trimIndent()

    private val AlbumsAsString = """
        {
        "id": 302127,
        "title": "Discovery",
        "cover": "https://api.deezer.com/album/302127/image",
        "cover_medium": "https://e-cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/250x250-000000-80-0-0.jpg",
        "tracklist": "https://api.deezer.com/album/302127/tracks",
        "artist": {
           "name": "Daft Punk"
                    }
        }
        """.trimIndent()

    private val RadioAsString = """
        {
           "id": "37151",
           "title": "Hits",
           "picture_medium": "https://e-cdns-images.dzcdn.net/images/misc/235ec47f2b21c3c73e02fce66f56ccc5/250x250-000000-80-0-0.jpg",
           "tracklist": "https://api.deezer.com/radio/37151/tracks"
        }
        """.trimIndent()

    private val ArtistsAsString = """
        {
           "id": "27",
           "name": "Daft Punk",
           "picture_medium": "https://e-cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/250x250-000000-80-0-0.jpg",
           "tracklist": "https://api.deezer.com/artist/27/top?limit=50"
        }
        """.trimIndent()

    @Test
    fun testSongSearch() {
        val songAsJSON = JSONObject(SongAsString)
        val song = Song(songAsJSON)

        val listOfSongs: List<Song> =  deezerService.requestSong("the real slim shady")
        assertEquals(song, listOfSongs.first())


    }

    @Test
    fun testAlbumSearch(){
        val albumAsJSON = JSONObject(AlbumsAsString)
        val album = Album(albumAsJSON)

        val listOfAlbum: List<Album> = deezerService.requestAlbum("Discovery")
        assertEquals(album, listOfAlbum.first())


    }

    @Test
    fun testRadioFetch(){
        val radioAsJSON = JSONObject(RadioAsString)
        val radio = Radio(radioAsJSON)

        val listOfRadio: List<Radio> = deezerService.requestRadio()
        assertEquals(radio,listOfRadio.first())


    }

    @Test
    fun testArtistFetch(){
        val artistAsJSON = JSONObject(ArtistsAsString)
        val artist = Artist(artistAsJSON)
        var listOfArtists: MutableList<Artist> = mutableStateListOf()


        listOfArtists = deezerService.requestArtist(27,listOfArtists) as MutableList<Artist>
        assertEquals(artist,listOfArtists.first())
    }


}